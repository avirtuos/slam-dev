package org.virtuoso.slam.localizers;

import org.virtuoso.slam.LocationScenario;
import org.virtuoso.slam.PointLocation;
import org.virtuoso.slam.PointMap;
import org.virtuoso.slam.Scan;
import org.virtuoso.slam.ScanAssembler;
import org.virtuoso.slam.Slam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractLocalizer
        implements Localizer
{
    protected static final int MAX_SIMS = 50;
    private final PointMap map;
    private final PointLocation location;
    private volatile List<LocationScenario> sims = new ArrayList<>();
    private volatile int version = 0;
    private volatile boolean enabled = false;

    private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1));

    public AbstractLocalizer(PointMap map, PointLocation location)
    {
        this.map = map;
        this.location = location;
    }

    @Override
    public void onScan(Scan scan)
    {
        try {
            executorService.submit(() -> {
                localize(scan);
            });
        }
        catch (RejectedExecutionException ex1) {}
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    protected abstract List<LocationScenario> doLocalize(Scan scan);

    private void localize(Scan scan)
    {
        if (!enabled) {
            //System.out.println("localize: disabled.");
            return;
        }
        long startTime = System.currentTimeMillis();
        if (map.getMap().getPoints().size() > 0) {
            List<LocationScenario> newSims = doLocalize(scan);
            Collections.sort(newSims);
            sims = newSims;
            version++;
            newSims.stream().forEach(next -> System.out.println("localize: x: " + next.getOrigin().getX() +
                    " y: " + next.getOrigin().getY() + " angle: " + next.getOrigin().getAngle() + " score: " +
                    next.getScore()));
        }

        System.out.println("localize: " + (System.currentTimeMillis() - startTime) / 1000 + " ms");
    }

    public int getVersion()
    {
        return version;
    }

    public List<LocationScenario> getPossibleLocations()
    {
        return sims;
    }
}

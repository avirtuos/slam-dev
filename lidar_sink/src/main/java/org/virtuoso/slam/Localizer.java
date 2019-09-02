package org.virtuoso.slam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Localizer
        implements ScanAssembler.ScanListener
{
    private static final int MAX_SIMS = 40;
    private final PointMap map;
    private final PointLocation location;
    private volatile List<LocationScenario> sims = new ArrayList<>();
    private volatile int version = 0;
    private volatile boolean enabled = true;

    private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1));

    public Localizer(PointMap map, PointLocation location)
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

    public void localize(Scan scan)
    {
        if (!enabled) {
            System.out.println("localize: disabled.");
            return;
        }
        long startTime = System.currentTimeMillis();
        if (map.getMap().getPoints().size() > 0) {
            List<LocationScenario> newSims = new ArrayList<>();
            try {
                int num = 0;
                for (int x = -100; x < 100; x++) {
                    for (int y = -100; y < 100; y++) {
                        for (int angle = 0; angle < 360; angle++) {
                            num++;
                            LocationScenario scenario = new LocationScenario(scan,
                                    location.get().getX() + x,
                                    location.get().getY() + y,
                                    angle);
                            scenario.setScore(match(scenario));
                            add(newSims, scenario);

                            if (num % 10_000 == 0) {
                                System.out.println("Progress: " + num);
                            }
                        }
                    }
                }
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
            }
            Collections.sort(newSims);
            sims = newSims;
            version++;
            newSims.stream().forEach(next -> System.out.println("localize: x: " + next.getOrigin().getX() +
                    " y: " + next.getOrigin().getY() + " angle: " + next.getOrigin().getAngle() + " score: " +
                    next.getScore()));
        }

        System.out.println("localize: " + (System.currentTimeMillis() - startTime) / 1000 + " ms");
    }

    public int getVersion(){
        return version;
    }

    public int match(LocationScenario scenario)
    {
        Scan mapScan = map.getMap();
        Scan scenarioScan = scenario.getScenario();
        int score = 0;
        for (Slam.Point next : scenarioScan.getPoints()) {
            if (mapScan.hasPoint(next)) {
                score++;
            }
        }

        return score;
    }

    public void add(List<LocationScenario> newSims, LocationScenario scenario)
    {
        if (scenario.getScore() < 100) {
            return;
        }

        if (MAX_SIMS > newSims.size()) {
            newSims.add(scenario);
        }

        for (int i = 0; i < newSims.size(); i++) {
            if (scenario.getScore() > newSims.get(i).getScore()) {
                newSims.remove(i);
                newSims.add(scenario);
                return;
            }
        }
    }

    public List<LocationScenario> getPossibleLocations()
    {
        return sims;
    }
}

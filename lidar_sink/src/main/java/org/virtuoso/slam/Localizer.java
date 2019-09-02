package org.virtuoso.slam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Localizer
        implements ScanAssembler.ScanListener
{
    private static final int MAX_SIMS = 20;
    private final PointMap map;
    private volatile List<LocationScenario> sims = new ArrayList<>();

    private final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1));

    public Localizer(PointMap map)
    {
        this.map = map;
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

    public void localize(Scan scan)
    {
        long startTime = System.currentTimeMillis();
        if (map.getMap().getPoints().size() > 0) {
            List<LocationScenario> newSims = new ArrayList<>();
            try {
                int num = 0;
                for (int x = -10; x < 10; x++) {
                    for (int y = -10; y < 10; y++) {
                        for (int angle = 0; angle < 360; angle+=4) {
                            num++;
                            LocationScenario scenario = new LocationScenario(scan, x, y, angle);
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
            sims = newSims;
        }

        System.out.println("localize: " + (System.currentTimeMillis() - startTime) / 1000 + " ms");
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
            }
        }
    }

    public List<LocationScenario> getPossibleLocations()
    {
        return sims;
    }
}

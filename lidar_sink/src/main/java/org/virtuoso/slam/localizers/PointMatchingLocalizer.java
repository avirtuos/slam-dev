package org.virtuoso.slam.localizers;

import org.virtuoso.slam.LocationScenario;
import org.virtuoso.slam.PointLocation;
import org.virtuoso.slam.PointMap;
import org.virtuoso.slam.Scan;
import org.virtuoso.slam.Slam;

import java.util.ArrayList;
import java.util.List;

public class PointMatchingLocalizer
        extends AbstractLocalizer
{
    private final PointMap map;
    private final PointLocation location;

    public PointMatchingLocalizer(PointMap map, PointLocation location)
    {
        super(map, location);
        this.map = map;
        this.location = location;
    }

    public List<LocationScenario> doLocalize(Scan scan)
    {
        List<LocationScenario> newSims = new ArrayList<>();
        int num = 0;
        for (int x = -25; x < 25; x++) {
            for (int y = -25; y < 25; y++) {
                for (float angle = -45; angle < 45; angle += 0.1F) {
                    num++;
                    LocationScenario scenario = new LocationScenario(scan,
                            location.get().getX() + x,
                            location.get().getY() + y,
                            angle);
                    scenario.setScore(match(scenario));
                    add(newSims, scenario);

                    if (num % 100_000 == 0) {
                        System.out.println("Progress: " + num);
                    }
                }
            }
        }
        return newSims;
    }

    public long match(LocationScenario scenario)
    {
        Scan mapScan = map.getMap();
        Scan scenarioScan = scenario.getScenario();
        long score = 0;
        for (Slam.Point next : scenarioScan.getPoints()) {
            if (mapScan.hasPoint(next)) {
                score++;
            }
        }

        return score;
    }

    protected long add(List<LocationScenario> newSims, LocationScenario scenario)
    {
        if (MAX_SIMS > newSims.size()) {
            newSims.add(scenario);
            return Integer.MIN_VALUE;
        }

        for (int i = 0; i < newSims.size(); i++) {
            if (scenario.getScore() > newSims.get(i).getScore()) {
                newSims.remove(i);
                newSims.add(scenario);

                long min = Integer.MIN_VALUE;
                for (LocationScenario next : newSims) {
                    if (next.getScore() > min) {
                        min = next.getScore();
                    }
                }
                return min;
            }
        }

        return  Integer.MIN_VALUE;
    }
}

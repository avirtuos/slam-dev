package org.virtuoso.slam.localizers;

import org.virtuoso.slam.LocationScenario;
import org.virtuoso.slam.PointLocation;
import org.virtuoso.slam.PointMap;
import org.virtuoso.slam.Scan;
import org.virtuoso.slam.Slam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinDistanceLocalizer
        extends AbstractLocalizer
{
    private final PointMap map;
    private final PointLocation location;

    public MinDistanceLocalizer(PointMap map, PointLocation location)
    {
        super(map, location);
        this.map = map;
        this.location = location;
    }

    private static class Scenario
    {
        private final int x;
        private final int y;
        private final float angle;

        public Scenario(int x, int y, float angle)
        {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }

        public float getAngle()
        {
            return angle;
        }
    }

    public List<LocationScenario> doLocalize(Scan scan)
    {

        long max = Integer.MAX_VALUE;
        List<LocationScenario> newSims = new ArrayList<>();
        List<Scenario> scenarios = new ArrayList<>();
        int num = 0;
        for (int x = -40; x < 40; x += 5) {
            for (int y = -40; y < 40; y += 5) {
                for (float angle = -90; angle < 90; angle += 5) {
                    scenarios.add(new Scenario(x, y, angle));
                }
            }
        }

        Collections.shuffle(scenarios);

        for (Scenario next : scenarios) {
            num++;
            LocationScenario scenario = new LocationScenario(scan,
                    location.get().getX() + next.getX(),
                    location.get().getY() + next.getY(),
                    next.getAngle());
            scenario.invert();
            scenario.setScore(match(max, scenario));
            max = add(newSims, scenario);

            if (num % 100 == 0) {
                System.out.println("Progress: " + num + " max: " + max);
            }
        }

        //Second Pass
        scenarios.clear();
        Collections.sort(newSims);
        newSims.stream().forEach(next -> System.out.println("2nd Pass - x: " + next.getOrigin().getX() + " y: " +
                next.getOrigin().getY() + " angle: " + next.getOrigin().getAngle()));
        for (int i = 0; i < 2; i++) {
            Slam.Point bestOrigin = newSims.get(i).getOrigin();
            System.out.println("2nd Pass - x: " + bestOrigin.getX() + " y: " +
                    bestOrigin.getY() + " angle: " + bestOrigin.getAngle());
            for (int x = bestOrigin.getX() - 5; x < bestOrigin.getX() + 5; x++) {
                for (int y = bestOrigin.getX() - 5; y < bestOrigin.getX() + 5; y++) {
                    for (float angle = bestOrigin.getAngle() - 6; angle < bestOrigin.getAngle() + 6; angle += 2) {
                        scenarios.add(new Scenario(x, y, angle));
                    }
                }
            }
        }

        Collections.shuffle(scenarios);

        for (Scenario next : scenarios) {
            num++;
            LocationScenario scenario = new LocationScenario(scan,
                    location.get().getX() + next.getX(),
                    location.get().getY() + next.getY(),
                    next.getAngle());
            scenario.setScore(match(max, scenario));
            scenario.invert();
            max = add(newSims, scenario);

            if (num % 100 == 0) {
                System.out.println(" 2nd Pass - Progress: " + num + " max: " + max);
            }
        }

        //Third Pass
        scenarios.clear();
        Collections.sort(newSims);
        newSims.stream().forEach(next -> System.out.println("3rd Pass - x: " + next.getOrigin().getX() + " y: " +
                next.getOrigin().getY() + " angle: " + next.getOrigin().getAngle()));
        for (int i = 0; i < 1; i++) {
            Slam.Point bestOrigin = newSims.get(i).getOrigin();
            System.out.println("3rd Pass - x: " + bestOrigin.getX() + " y: " +
                    bestOrigin.getY() + " angle: " + bestOrigin.getAngle());
            for (float angle = bestOrigin.getAngle() - 2; angle < bestOrigin.getAngle() + 2; angle += 0.1F) {
                scenarios.add(new Scenario(bestOrigin.getX(), bestOrigin.getY(), angle));
            }
        }

        Collections.shuffle(scenarios);
        for (Scenario next : scenarios) {
            num++;
            LocationScenario scenario = new LocationScenario(scan,
                    location.get().getX() + next.getX(),
                    location.get().getY() + next.getY(),
                    next.getAngle());
            scenario.setScore(match(max, scenario));
            scenario.invert();
            max = add(newSims, scenario);

            if (num % 100 == 0) {
                System.out.println(" 2rd Pass - Progress: " + num + " max: " + max);
            }
        }

        System.out.println("MinDistanceLocalizer: total scenarios " + num);
        return newSims;
    }

    public long match(long max, LocationScenario scenario)
    {
        Scan mapScan = map.getMap();
        Scan scenarioScan = scenario.getScenario();
        double totalDistance = 0;
        for (Slam.Point next : scenarioScan.getPoints()) {
            double minDistance = Integer.MAX_VALUE;
            for (Slam.Point nextMap : mapScan.getPoints()) {
                double x = Math.pow(nextMap.getX() - next.getX(), 2);
                double y = Math.pow(nextMap.getY() - next.getY(), 2);
                double distance = Math.sqrt(x + y);
                if (minDistance > distance) {
                    minDistance = distance;
                }
            }
            totalDistance += minDistance;
            if (totalDistance > max) {
                return Integer.MAX_VALUE;
            }
        }

        return Math.round(totalDistance);
    }

    protected long add(List<LocationScenario> newSims, LocationScenario scenario)
    {
        long maxVal = Integer.MAX_VALUE;
        if (MAX_SIMS > newSims.size()) {
            newSims.add(scenario);
            return maxVal;
        }

        for (int i = 0; i < newSims.size(); i++) {
            if (scenario.getScore() < newSims.get(i).getScore()) {
                newSims.remove(i);
                newSims.add(scenario);

                long max = Integer.MAX_VALUE;
                for (LocationScenario next : newSims) {
                    if (next.getScore() < max) {
                        max = next.getScore();
                    }
                }
                return max;
            }

            if (newSims.get(i).getScore() < maxVal) {
                maxVal = newSims.get(i).getScore();
            }
        }

        return maxVal;
    }
}

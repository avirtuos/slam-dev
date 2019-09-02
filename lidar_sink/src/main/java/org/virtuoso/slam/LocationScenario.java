package org.virtuoso.slam;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class LocationScenario
{
    private final Scan scenario = new Scan();
    private final Slam.Point origin;
    private volatile int score = 0;

    public LocationScenario(Scan scan, int x, int y, float angle)
    {
        origin = Slam.Point.newBuilder().setAngle(angle).setDistance(0).setX(x).setY(y).setEnd(false).build();

        for (Slam.Point next : scan.getPoints()) {
            float effectiveAngle = next.getAngle() + angle;
            if (effectiveAngle > 359) {
                effectiveAngle = effectiveAngle - 359;
            }
            int x1 = x + (int) Math.round(sin(effectiveAngle) * next.getDistance());
            int y1 = y + (int) Math.round(cos(effectiveAngle) * next.getDistance());

            scenario.add(Slam.Point.newBuilder()
                    .setAngle(effectiveAngle)
                    .setDistance(next.getDistance())
                    .setX(x1)
                    .setY(y1)
                    .setEnd(false)
                    .build());
        }
    }

    public Scan getScenario()
    {
        return scenario;
    }

    public Slam.Point getOrigin()
    {
        return origin;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}

package org.virtuoso.slam;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class LocationScenario
        implements Comparable<LocationScenario>
{
    private final Scan scenario = new Scan();
    private final Slam.Point origin;
    private volatile long score = 0;
    private boolean invert = false;

    public LocationScenario(Scan scan, int x, int y, float angle)
    {
        origin = Slam.Point.newBuilder().setAngle(angle).setDistance(0).setX(x).setY(y).setEnd(false).build();

        for (Slam.Point next : scan.getPoints()) {
            double effectiveAngle = ((angle * 3.14159265359) / 180) + next.getAngle();
            if (effectiveAngle > 6.28319) {
                effectiveAngle = effectiveAngle - 6.28319;
            }
            else if (effectiveAngle < 0) {
                effectiveAngle = effectiveAngle * -1;
            }
            int x1 = x + (int) Math.round(sin(effectiveAngle) * next.getDistance());
            int y1 = y + (int) Math.round(cos(effectiveAngle) * next.getDistance());

            scenario.add(Slam.Point.newBuilder()
                    .setAngle((float) effectiveAngle)
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

    public long getScore()
    {
        return score;
    }

    public void setScore(long score)
    {
        this.score = score;
    }

    public void invert()
    {
        this.invert = true;
    }

    @Override
    public int compareTo(LocationScenario o)
    {
        if (invert) {
            return Long.compare(this.getScore(), o.getScore());
        }
        else {
            return Long.compare(o.getScore(), this.getScore());
        }
    }
}

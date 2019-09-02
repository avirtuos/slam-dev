package org.virtuoso.slam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PointLocation
{
    private final List<Slam.Point> location = new ArrayList<>();

    public PointLocation()
    {
        location.add(Slam.Point.newBuilder().setX(0).setY(0).setAngle(0).setEnd(false).setDistance(0).build());
    }

    public synchronized void update(Slam.Point point)
    {
        location.add(point);
    }

    public synchronized Slam.Point get()
    {
        return location.get(location.size() - 1);
    }

    public synchronized List<Slam.Point> getHistory()
    {
        return Collections.unmodifiableList(location);
    }
}

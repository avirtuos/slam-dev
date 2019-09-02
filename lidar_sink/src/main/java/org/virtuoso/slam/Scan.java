package org.virtuoso.slam;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scan
{
    private Map<String, Slam.Point> points = new HashMap<>();

    private String makeKey(Slam.Point point)
    {
        return point.getX() + ":" + point.getY();
    }

    public void add(Slam.Point point)
    {
        points.putIfAbsent(makeKey(point), point);
    }

    public Collection<Slam.Point> getPoints()
    {
        return Collections.unmodifiableCollection(points.values());
    }

    public boolean hasPoint(Slam.Point point)
    {
        return points.containsKey(makeKey(point));
    }

    public Scan merge(Scan scan)
    {
        Scan merged = new Scan();

        for (Slam.Point point : this.getPoints()) {
            merged.add(point);
        }

        for (Slam.Point point : scan.getPoints()) {
            if (!merged.hasPoint(point)) {
                merged.add(point);
            }
        }

        return merged;
    }
}

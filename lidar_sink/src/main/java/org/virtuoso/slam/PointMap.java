package org.virtuoso.slam;

import java.util.ArrayList;
import java.util.List;

public class PointMap
{
    private final List<Scan> map = new ArrayList<>();

    public PointMap()
    {
        map.add(new Scan());
    }

    public synchronized void apply(Scan scan)
    {
        map.add(map.get(map.size() - 1).merge(scan));
    }

    public synchronized Scan getMap()
    {
        return map.get(map.size() - 1);
    }
}

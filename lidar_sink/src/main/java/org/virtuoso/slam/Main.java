package org.virtuoso.slam;

import org.virtuoso.slam.localizers.MinDistanceLocalizer;
import org.virtuoso.slam.localizers.PointMatchingLocalizer;

public class Main
{

    public static void main(String[] args)
    {
        PointMap map = new PointMap();
        PointLocation location = new PointLocation();
        PointMatchingLocalizer pointMatchingLocalizer = new PointMatchingLocalizer(map, location);
        MinDistanceLocalizer minDistanceLocalizer = new MinDistanceLocalizer(map, location);
        ScanRenderer scanRenderer = new ScanRenderer(map, location);
        ScanAssembler scanAssembler = new ScanAssembler();
        scanAssembler.addListener(scanRenderer);
        scanAssembler.addListener(pointMatchingLocalizer);
        scanAssembler.addListener(minDistanceLocalizer);

        LocationRenderer pointMatchingLocation = new LocationRenderer(map, location, pointMatchingLocalizer, "PointMatching Location");
        LocationRenderer minDistanceLocation = new LocationRenderer(map, location, minDistanceLocalizer, "DistanceMinimizing Location");

        LidarSource lidarSource = new LidarSource();
        lidarSource.attach(scanAssembler);
    }
}

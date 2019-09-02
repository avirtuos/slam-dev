package org.virtuoso.slam;

public class Main
{

    public static void main(String[] args)
    {
        PointMap map = new PointMap();
        PointLocation location = new PointLocation();
        Localizer localizer = new Localizer(map, location);
        ScanRenderer scanRenderer = new ScanRenderer(map, location);
        ScanAssembler scanAssembler = new ScanAssembler();
        scanAssembler.addListener(scanRenderer);
        scanAssembler.addListener(localizer);

        LocationRenderer locationRenderer = new LocationRenderer(map, location, localizer);
        LidarSource lidarSource = new LidarSource();
        lidarSource.attach(scanAssembler);
    }
}

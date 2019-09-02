package org.virtuoso.slam;

public class Main
{

    public static void main(String[] args)
    {
        PointMap map = new PointMap();
        Localizer localizer = new Localizer(map);
        ScanRenderer scanRenderer = new ScanRenderer(map);
        ScanAssembler scanAssembler = new ScanAssembler();
        scanAssembler.addListener(scanRenderer);
        scanAssembler.addListener(localizer);

        LocationRenderer locationRenderer = new LocationRenderer(map, localizer);
        LidarSource lidarSource = new LidarSource();
        lidarSource.attach(scanAssembler);

    }
}

package org.virtuoso.slam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ScanAssembler
        implements LidarSource.PointListener
{
    private volatile Scan inProgressScan = new Scan();
    private final List<ScanListener> listeners = new ArrayList<>();
    private final AtomicLong numConflations = new AtomicLong(0);
    private final ExecutorService dispatcher = Executors.newFixedThreadPool(5);

    public interface ScanListener
    {
        void onScan(Scan scan);
    }

    public void addListener(ScanListener listener)
    {
        listeners.add(listener);
    }

    public void onPoint(Slam.Point point)
    {
        if (point.getEnd()) {
            numConflations.incrementAndGet();
        }

        if (numConflations.get() < 20) {
            inProgressScan.add(point);
        }
        else {
            Scan fullScan = inProgressScan;
            inProgressScan = new Scan();
            numConflations.set(0);
            listeners.stream().forEach(next -> dispatcher.submit(() -> next.onScan(fullScan)));
        }
    }
}
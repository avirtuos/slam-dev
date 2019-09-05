package org.virtuoso.slam.localizers;

import org.virtuoso.slam.LocationScenario;
import org.virtuoso.slam.Scan;
import org.virtuoso.slam.ScanAssembler;
import org.virtuoso.slam.Slam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public interface Localizer
        extends ScanAssembler.ScanListener
{
    void onScan(Scan scan);

    void setEnabled(boolean enabled);

    boolean isEnabled();

    int getVersion();

    List<LocationScenario> getPossibleLocations();
}

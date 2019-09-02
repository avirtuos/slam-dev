package org.virtuoso.slam;

import java.io.IOException;
import java.net.Socket;

public class LidarSource
{
    public interface PointListener
    {
        void onPoint(Slam.Point point);
    }

    public void attach(PointListener listener)
    {
        while (true) {
            try (Socket socket = new Socket("jetson.localdomain", 8080)) {
                while (socket.isConnected() && !socket.isClosed()) {
                    Slam.Point point = Slam.Point.parseDelimitedFrom(socket.getInputStream());
                    socket.getInputStream().read();
                    if (point != null) {
                        listener.onPoint(point);
                    }
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
                try {
                    Thread.sleep(5_000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package le.arn.org.virtuoso.slam;

import com.google.protobuf.InvalidProtocolBufferException;
import le.arn.GreetingProtos;
import org.virtuoso.slam.Slam;

import java.io.IOException;
import java.net.*;

public class LidarSource
{

    public void attach()
    {
        try (Socket socket = new Socket("jetson.localdomain", 8080)) {
            while (socket.isConnected() && !socket.isClosed()) {
                Slam.TelemetryPoint point = Slam.TelemetryPoint.parseDelimitedFrom(socket.getInputStream());
                socket.getInputStream().read();
                System.out.println("point= " + point.getX() + " " + point.getY() + " " + point.getAngle() + " " + point.getDistance());
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

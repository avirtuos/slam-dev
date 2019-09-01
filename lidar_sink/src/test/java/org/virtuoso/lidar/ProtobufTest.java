package org.virtuoso.lidar;

import le.arn.GreetingProtos;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ProtobufTest
{
    private static final String HELLO_WORLD = "Hello World";
    private static final String SER_FILE = "/tmp/test.txt";

    @Test
    public void test()
    {

        // 1 : Create a Greeting object using the Protobuf builder.
        GreetingProtos.Greeting.Builder greetingBuilder = GreetingProtos.Greeting.newBuilder();
        greetingBuilder.setGreeting(HELLO_WORLD);
        GreetingProtos.Greeting greeting = greetingBuilder.build();
        try {
            // 2 : Write the message into a file. Serialize the object.
            FileOutputStream output = new FileOutputStream(SER_FILE);
            greeting.writeTo(output);
            output.close();
            // 3 : Deserialize the object from the file.
            GreetingProtos.Greeting greetingFromFile = GreetingProtos.Greeting.parseFrom(new FileInputStream(
                    SER_FILE));
            System.out.println("We read this from the file " + greetingFromFile);
            // 4 : All is well?
            assertEquals(HELLO_WORLD, greetingFromFile.getGreeting());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

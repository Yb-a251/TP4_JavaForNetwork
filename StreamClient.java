import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** Client used to test the TCP byte stream. */
public class StreamClient {

    public static void main(String[] args) throws IOException {

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        int test = Integer.parseInt(args[2]);

        Socket socket = new Socket(host, port);
        System.out.println("Connected to the server.");

        OutputStream output = socket.getOutputStream();

        if (test == 1) {

            // Send three small messages without line breaks
            byte[] message1 = "Hello".getBytes(StandardCharsets.UTF_8);
            byte[] message2 = "World".getBytes(StandardCharsets.UTF_8);
            byte[] message3 = "Java".getBytes(StandardCharsets.UTF_8);

            output.write(message1);
            output.write(message2);
            output.write(message3);

            System.out.println("3 messages sent.");

        } else if (test == 2) {

            // Send 10000 bytes in one write
            byte[] data = new byte[10000];

            output.write(data);

            System.out.println("10000 bytes sent.");
        }

        output.flush();
        socket.close();
    }
}
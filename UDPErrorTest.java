import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** Tests UDP when the server is not running. */
public class UDPErrorTest {

    public static void main(String[] args) throws IOException {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        InetAddress address = InetAddress.getByName(host);
        DatagramSocket socket = new DatagramSocket();

        socket.connect(address, port);

        // Wait a maximum of 3 seconds for a response
        socket.setSoTimeout(3000);

        String message = "Hello";
        byte[] data = message.getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

        socket.send(packet);
        System.out.println("Message sent.");

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(response);
            System.out.println("Response received.");

        } catch (SocketTimeoutException e) {
            System.out.println("No response from the server.");

        } catch (PortUnreachableException e) {
            System.out.println("Port unreachable.");
        }

        socket.close();
    }
}
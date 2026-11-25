import java.io.*;
import java.net.*;

/** Server used to test the TCP byte stream. */
public class StreamServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        int port = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Waiting for a client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        InputStream input = socket.getInputStream();

        byte[] buffer = new byte[1024];

        // Wait before reading the data
        Thread.sleep(1000);

        int bytesRead = input.read(buffer);
        int total = 0;

        while (bytesRead != -1) {

            System.out.println("Bytes received: " + bytesRead);

            total = total + bytesRead;

            bytesRead = input.read(buffer);
        }

        System.out.println("Total bytes received: " + total);

        socket.close();
        serverSocket.close();
    }
}
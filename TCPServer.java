import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** TCP server that receives and echoes messages. */
public class TCPServer {

    private static final int DEFAULT_PORT = 8080;
    private final int port;

    /** Creates a server with the given port. */
    public TCPServer(int port) {
        this.port = port;
    }

    /** Creates a server with the default port. */
    public TCPServer() {
        this(DEFAULT_PORT);
    }

    /** Starts the server. */
    public void launch() {
        System.out.println(this);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);

            while (true) {

                System.out.println("Waiting for a client...");

                // Wait for a client
                Socket socket = serverSocket.accept();

                try {
                    InetAddress address = socket.getInetAddress();
                    String clientIP = address.getHostAddress();

                    System.out.println("Client connected: " + clientIP);

                    // Input stream
                    InputStream input = socket.getInputStream();
                    InputStreamReader inputReader = new InputStreamReader(input, StandardCharsets.ISO_8859_1);
                    BufferedReader reader = new BufferedReader(inputReader);

                    // Output stream
                    OutputStream output = socket.getOutputStream();
                    OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.ISO_8859_1);
                    PrintWriter writer = new PrintWriter(outputWriter, true);

                    // Read the first message
                    String message = reader.readLine();

                    while (message != null) {

                        System.out.println("Received: " + message);

                        // Send the message back with the client IP
                        String response = clientIP + ": " + message;
                        writer.println(response);

                        // Read the next message
                        message = reader.readLine();
                    }

                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());

                } finally {

                    // Close the client connection
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println("Error closing the socket.");
                    }

                    System.out.println("Client disconnected.");
                }
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    /** Returns the server information. */
    @Override
    public String toString() {
        return "TCP Server - Port: " + port;
    }

    public static void main(String[] args) {
        TCPServer server;

        // Use the default port if no argument is given
        if (args.length > 0) {
            server = new TCPServer(Integer.parseInt(args[0]));
        } else {
            server = new TCPServer();
        }

        server.launch();
    }
}
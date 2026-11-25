import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/** TCP client that exchanges messages with the server. */
public class TCPClient {

    private final String host;
    private final int port;

    /** Creates a client with the given host and port. */
    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /** Connects to the server and exchanges messages. */
    public void start() {

        try (Socket socket = new Socket(host, port)) {

            System.out.println("Connected to " + host + ":" + port);

            // Input stream
            InputStream input = socket.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, StandardCharsets.ISO_8859_1);
            BufferedReader reader = new BufferedReader(inputReader);

            // Output stream
            OutputStream output = socket.getOutputStream();
            OutputStreamWriter outputWriter = new OutputStreamWriter(output, StandardCharsets.ISO_8859_1);
            PrintWriter writer = new PrintWriter(outputWriter, true);

            // Read messages from the keyboard
            InputStreamReader keyboardInput = new InputStreamReader(System.in, StandardCharsets.ISO_8859_1);
            BufferedReader keyboard = new BufferedReader(keyboardInput);

            System.out.println("Enter a message or /quit to exit:");

            String message = keyboard.readLine();

            while (message != null && !message.equals("/quit")) {

                // Send the message to the server
                writer.println(message);

                // Read the server response
                String response = reader.readLine();

                // Check if the server closed the connection
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                System.out.println(response);

                // Read the next message
                message = keyboard.readLine();
            }

            System.out.println("Client disconnected.");

        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + host);

        } catch (ConnectException e) {
            System.out.println("Unable to connect to the server.");

        } catch (SocketException e) {
            System.out.println("Connection lost.");

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }   

    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        TCPClient client = new TCPClient(host, port);
        client.start();
    }
}

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Server extends Thread {

    protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10008);
            System.out.println("Connection Socket Created");
            try {
                while (true) {
                    System.out.println("Waiting for Connection");
                    new Server(serverSocket.accept());
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }

    private Server(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
            OutputStream ostream = clientSocket.getOutputStream();
            // PrintWriter pwrite = new PrintWriter(ostream, true);

            String inputLine, sendMessage;

            String ClientIP = clientSocket.getRemoteSocketAddress().toString();
            System.out.println(ClientIP + " is  now connected. Have fun!");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client " + ClientIP + ": " + inputLine);

                if (inputLine.equals("Bye.")) {
                    break;
                }
                sendMessage = keyRead.readLine();
                out.println(sendMessage);
                out.flush();

            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}
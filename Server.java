
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            String inputLine, sendMessage;

            String ClientIP = clientSocket.getRemoteSocketAddress().toString();
            System.out.println(ClientIP + " is  now connected. Have fun!");

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client " + ClientIP + ": " + inputLine);
                String ans = analyzer(inputLine);

                if (inputLine.equals("Bye.")) {
                    break;
                }

                sendMessage = ans;
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

    private String analyzer(String op) {

        String[] operation = op.split(" ");
        float ans = 0;

        switch (operation[1]) {
            case "+":
                ans = Float.parseFloat(operation[0]) + Float.parseFloat(operation[2]);
                break;
            case "-":
                ans = Float.parseFloat(operation[0]) - Float.parseFloat(operation[2]);
                break;
            case "/":
                ans = Float.parseFloat(operation[0]) / Float.parseFloat(operation[2]);
                break;
            case "*":
                ans = Float.parseFloat(operation[0]) * Float.parseFloat(operation[2]);
                break;

        }

        return String.valueOf(ans);
    }
}
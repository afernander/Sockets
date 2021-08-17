import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String("52.3.7.32");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("52.3.7.32", 10008);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the calculator in TCP Socket (you can quit with \"q\"");
        while (true) {
            String operation[] = menu();
            if (operation[0].equalsIgnoreCase("q") || operation[1].equalsIgnoreCase("q")
                    || operation[2].equalsIgnoreCase("q"))
                break;
            out.println(operation[0] + " " + operation[1] + " " + operation[2]);
            System.out.println("Server : " + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    private static String[] menu() throws IOException {

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String word = " ";
        String[] operation = new String[3];
        System.out.println("Plese enter a number");
        word = stdIn.readLine();
        if (word.equalsIgnoreCase("q")) {
            operation[0] = "q";
            return operation;
        } else {
            operation[0] = String.valueOf(String.valueOf(word));
            word = "";
        }

        do {
            System.out.println("Enter an operation");
            String option = stdIn.readLine();
            if ((option.equals("+") || option.equals("-") || option.equals("/") || option.equals("*"))) {
                operation[1] = String.valueOf(option);
                break;
            } else {
                System.out.println("Enter a valid operation");
            }
        } while (true);
        System.out.println("Enter another number");
        word = stdIn.readLine();
        if (word.equalsIgnoreCase("q")) {
            operation[2] = "q";
            return operation;
        } else {
            operation[2] = String.valueOf(String.valueOf(word));
            word = "";
        }
        return operation;

    }
}

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String("127.0.0.1");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 10008);
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
        String userInput;

        System.out.println("Type Message (\"Bye.\" to quit)");
        while ((userInput = stdIn.readLine()) != null) {
            String operation[] = menu();
            // out.println(userInput);
            out.println(operation[0] + " " + operation[1] + " " + operation[2]);
            if (userInput.equals("Bye."))
                break;

            System.out.println("Server : " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    private static String[] menu() throws IOException {

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String[] operation = new String[3];
        System.out.println("Plese enter a number");
        operation[0] = String.valueOf(stdIn.readLine());
        do  {
            System.out.println("Enter an operation");
            String option = stdIn.readLine();
            if ((option.equals("+") || option.equals("-")|| option.equals("/") || option.equals("*"))) {
                operation[1] = String.valueOf(option);
                break;
            }else{
                System.out.println("Enter a valid operation");
            }
        }while (true);
        System.out.println("Enter another number");
        operation[2] = stdIn.readLine();
        return operation;

    }
}

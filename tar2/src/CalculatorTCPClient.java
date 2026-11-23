import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class CalculatorTCPClient {
  public static void main(String[] args) {
    System.out.println("Client connecting to localhost:9090");
    try (Socket client = new Socket("localhost", 9090);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner scanner = new Scanner(System.in)) {

      System.out.println("Connected to server. Type a simple arithmetic equation (or \"exit\" to quit):");

      while (true) {
        String message = scanner.nextLine();
        if (message.equalsIgnoreCase("exit")) {
          out.println(message);
          System.out.println("Closing connection");
          break;
        }

        out.println(message);
        System.out.println("Sent " + message + " To Server");

        String reply = in.readLine();
        if (reply == null) {
          System.out.println("Server Lost connection");
          break;
        }
        System.out.println(message + " = " + reply);
      }
    } catch (IOException e) {
      System.out.println("Client Error: " + e.getMessage());
    }
    System.out.println("Client stopped.");
  }
}

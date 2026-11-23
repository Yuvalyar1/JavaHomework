import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CalculatorTCPServer {
  public static void main(String[] args) {
    System.out.println("Attempting to start server on port 9090");
    try (ServerSocket socket = new ServerSocket(9090)) {
      System.out.println("Server is Listening on port 9090");
      // All server objects go into the "try" condititon,
      // as all can fail and one cant live without the other.
      // seperated by semicolon
      try (Socket clientSocket = socket.accept(); // Wait until a client connects and retuns it's socket
          // Read text from client. buffered reader expects a stream reader,
          // stream reader expects a stream
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { // Sends text back to client
                                                                                     // (with auto-flush)
        System.out.println("Client connection Successful");
        String inputLine;
        Float ans = null; // Float object CAN be null
        while ((inputLine = in.readLine()) != null) { // Client disconnected
          inputLine = inputLine.trim(); // Trimming trailing spaces
          System.out.println("Received from client: '" + inputLine + "'");

          if (inputLine.toLowerCase().equals("exit")) // Client exited
            break;

          String[] split = inputLine.split(" "); // [num1, operator, num2]
          try {
            if (split.length != 3)
              throw new IllegalArgumentException("Invalid Expression");
            float num1;
            float num2;
            if (split[0].equals("ANS")) {
              if (ans != null)
                num1 = ans;
              else
                throw new IllegalArgumentException("No Previous Result");
            } else {
              num1 = Float.parseFloat(split[0]);
            }
            if (split[2].equals("ANS"))
              if (ans != null)
                num2 = ans;
              else
                throw new IllegalArgumentException("No Previous Result");
            else {
              num2 = Float.parseFloat(split[2]);
            }

            switch (split[1]) {
              case "*":
                ans = (num1 * num2);
                out.println(ans);
                break;

              case "/":
                if (num2 == 0) {
                  throw new ArithmeticException("Division by 0");
                }
                ans = (num1 / num2);
                out.println(ans);
                break;

              case "+":
                ans = (num1 + num2);
                out.println(ans);
                break;

              case "-":
                ans = (num1 - num2);
                out.println(ans);
                break;

              default:
                throw new IllegalArgumentException("Invalid Operator");
            }

          } catch (NumberFormatException e) {
            out.println("Error: " + e.getMessage());
            continue;
          } catch (IllegalArgumentException e) {
            out.println("Error: " + e.getMessage());
            continue;
          } catch (ArithmeticException e) {
            out.println("Error: " + e.getMessage());
            continue;
          }

        }
      }
    } catch (IOException e) {
      System.out.println("Server Error: " + e);
    }
  }
}

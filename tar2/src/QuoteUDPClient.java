import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.Scanner;

public class QuoteUDPClient {
  public static void main(String[] args) {
    try (DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in)) {
      InetAddress serverAddress = InetAddress.getByName("localhost");
      byte[] receiveData = new byte[1024];

      while (true) {
        System.out.println("Enter GET for quote or exit to quit: ");
        String message = scanner.nextLine().trim();

        if (!message.equalsIgnoreCase("GET") && !message.equalsIgnoreCase("exit")) {
          System.out.println("Bad input, try again");
          continue;
        }
        // Remember for UDP data is sent in an array of bytes,
        // if longer than array, it gets split
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8080);
        socket.send(sendPacket);

        if (message.equalsIgnoreCase("exit")) {
          System.out.println("Client finished");
          break;
        }

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Quote received: " + reply);
      }
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}

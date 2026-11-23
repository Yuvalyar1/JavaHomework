import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;
import java.util.Random;

public class QuoteUDPServer {
  public static void main(String[] args) {
    String[] quotes = {
        "There are no accidents. - Master Oogway",
        "Sucking at something is the first step to becoming sorta good at something. – Jake",
        "Ohana means family. Family means nobody gets left behind or forgotten. – Stitch",
        "Anything is possible when you have inner peace. – Master Shifu",
        "Just keep swimming. – Dory"
    };
    Random rand = new Random();

    try (DatagramSocket socket = new DatagramSocket(8080)) { // Opens UDP socket on port 8080
      System.out.println("UDP Quote server listening on port 8080");
      byte[] receiveData = new byte[1024];

      while (true) {
        // Fresh packet each loop so receive length resets to full buffer
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket); // Waits until a packet arrives from a client

        // Read only the bytes actually received, not the empty rest of the buffer
        String message = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
        System.out.println("Request recieved: " + message);

        if (message.equalsIgnoreCase("exit")) {
          System.out.println("Server shutting down");
          break;
        }

        String reply;
        if (message.equalsIgnoreCase("GET")) {
          reply = quotes[rand.nextInt(quotes.length)];
        } else {
          reply = "Type 'GET' for wisdom, Type 'exit' if you are wise";
        }

        byte[] sendData = reply.getBytes();
        // Send reply back using the client's IP and port from the incoming packet
        DatagramPacket sendPacket = new DatagramPacket(
            sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
        socket.send(sendPacket);
      }
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }
}

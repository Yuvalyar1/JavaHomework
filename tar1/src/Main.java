import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Message> repository = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        //Board Messages
        repository.add(new BoardMessage("Arthur", "Gather Around", new Date(), -1));
        repository.add(new BoardMessage("Lancelot", "Ready your swords", new Date(), 0));

        //Email Messages
        try {
            EmailMessage email1 = new EmailMessage("Percival", "Who forgot his helmet?", "Lost helmet");
            email1.addAttachment(new File("helmet.png", "png"));

            EmailMessage email2 = new EmailMessage("Alymere", "My Helmet, coming", "Helmet");
            email2.addAttachment(new File("selfie.png", "png"));

            repository.add(email1);
            repository.add(email2);
        } catch (Exception e) {
            System.out.println("Error setting up emails: " + e.getMessage());
        }

        // SMS Messages
        try {
            repository.add(new SMSMessage("Arthur", "Where are you?", "0501234567"));
            repository.add(new SMSMessage("Mordred", "Running late.", "0529876543"));
        } catch (Exception e) {
            System.out.println("Error setting up SMS: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add SMS Message");
            System.out.println("2. Delete Message");
            System.out.println("3. Print all messages");
            System.out.println("4. Search messages by word");
            System.out.println("5. Print Digital Messages");
            System.out.println("6. Show Previews");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); //buffer flush, like needed in C for trailing \n, this one is for a good input

                switch (choice) {
                    case 1:
                        System.out.print("Sender: ");
                        String sender = scanner.nextLine();
                        System.out.print("Content: ");
                        String content = scanner.nextLine();
                        System.out.print("Phone (starts with 05): ");
                        String phone = scanner.nextLine();

                        try {
                            repository.add(new SMSMessage(sender, content, phone));
                            System.out.println("Added.");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Index to delete: ");
                        int index = scanner.nextInt();
                        scanner.nextLine(); // Buffer problems here as well, another flush
                        if (index >= 0 && index < repository.size()) {
                            repository.remove(index);
                            System.out.println("Deleted.");
                        } else {
                            System.out.println("Invalid index.");
                        }
                        break;

                    case 3:
                        for (Message m : repository) {
                            System.out.println(m.toString());
                            System.out.println("---");
                        }
                        break;

                    case 4:
                        System.out.print("Enter word to search: ");
                        String searchWord = scanner.nextLine();
                        ArrayList<String> searchWords = new ArrayList<>();
                        searchWords.add(searchWord);

                        int matchCount = 0;
                        for (Message m : repository) {
                            if (m.find(searchWords)) {
                                matchCount++;
                            }
                        }
                        System.out.println("Messages containing word: " + matchCount);
                        break;

                    case 5:
                        for (Message m : repository) {
                            if (m instanceof IDigital) {
                                IDigital digitalMsg = (IDigital) m;
                                System.out.println(digitalMsg.printCommunicationMethod() + " | From: " + m.getSender());
                            }
                        }
                        break;

                    case 6:
                        for (Message m : repository) {
                            if (m instanceof BoardMessage)((BoardMessage) m).addView();
                            System.out.println(m.generatePreview());
                        }
                        break;

                    case 7:
                        running = false;
                        System.out.println("Exiting.");
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {

                System.out.println("Invalid option.");
                scanner.nextLine(); // Buffer flush, but for a bad input
            }
        }
        scanner.close();
    }
}
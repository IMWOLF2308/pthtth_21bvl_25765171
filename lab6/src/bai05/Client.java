package bai05;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            ChatService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (ChatService) registry.lookup("ChatService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (ChatService) registry.lookup("ChatService");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Chuong trinh Chat (Nhap 'ket thuc' hoac 'exit' de dung)");
            while (true) {
                System.out.print("Client: ");
                String message = scanner.nextLine();
                if (message.trim().equalsIgnoreCase("ket thuc") || message.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Da dong phien chat.");
                    break;
                }
                service.sendMessage(message);
                String reply = service.getReply();
                System.out.println("Server phan hoi: " + reply);
            }
            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}

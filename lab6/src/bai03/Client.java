package bai03;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            PrimeService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (PrimeService) registry.lookup("PrimeService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (PrimeService) registry.lookup("PrimeService");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhap vao mot so nguyen: ");
            int number = scanner.nextInt();

            boolean result = service.isPrime(number);
            if (result) {
                System.out.println(number + " la so nguyen to.");
            } else {
                System.out.println(number + " khong phai la so nguyen to.");
            }
            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}

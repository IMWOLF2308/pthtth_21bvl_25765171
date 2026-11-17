package bai02;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            CalculatorService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (CalculatorService) registry.lookup("CalculatorService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (CalculatorService) registry.lookup("CalculatorService");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhap so thu nhat: ");
            int firstNumber = scanner.nextInt();
            System.out.print("Nhap so thu hai: ");
            int secondNumber = scanner.nextInt();

            int sum = service.add(firstNumber, secondNumber);
            System.out.println("Tong hai so la: " + sum);
            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}

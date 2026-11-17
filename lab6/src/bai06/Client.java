package bai06;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            BankService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (BankService) registry.lookup("BankService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (BankService) registry.lookup("BankService");
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----- NGAN HANG RMI -----");
                System.out.println("1. Kiem tra so du");
                System.out.println("2. Gui tien");
                System.out.println("3. Rut tien");
                System.out.println("0. Thoat");
                System.out.print("Chon chuc nang: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    double currentBalance = service.getBalance();
                    System.out.println("So du hien tai: " + currentBalance + " VND");
                } else if (choice == 2) {
                    System.out.print("Nhap so tien muon gui: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    service.deposit(amount);
                    System.out.println("Gui tien thanh cong.");
                } else if (choice == 3) {
                    System.out.print("Nhap so tien muon rut: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    boolean success = service.withdraw(amount);
                    if (success) {
                        System.out.println("Rut tien thanh cong.");
                    } else {
                        System.out.println("Rut tien that bai. So du khong du hoac so tien khong hop le.");
                    }
                } else if (choice == 0) {
                    System.out.println("Thoat chuong trinh.");
                    break;
                }
            }
            scanner.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}

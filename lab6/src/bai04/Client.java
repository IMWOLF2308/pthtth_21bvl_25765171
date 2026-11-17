package bai04;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            ContactService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (ContactService) registry.lookup("ContactService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (ContactService) registry.lookup("ContactService");
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----- QUAN LY DANH BA -----");
                System.out.println("1. Them lien he");
                System.out.println("2. Tim kiem lien he");
                System.out.println("3. Xoa lien he");
                System.out.println("0. Thoat");
                System.out.print("Chon chuc nang: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Nhap ten: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhap so dien thoai: ");
                    String phone = scanner.nextLine();
                    service.addContact(name, phone);
                    System.out.println("Them lien he thanh cong.");
                } else if (choice == 2) {
                    System.out.print("Nhap ten can tim: ");
                    String name = scanner.nextLine();
                    String phone = service.findContact(name);
                    if (phone != null) {
                        System.out.println("So dien thoai cua " + name + " la: " + phone);
                    } else {
                        System.out.println("Khong tim thay lien he voi ten: " + name);
                    }
                } else if (choice == 3) {
                    System.out.print("Nhap ten can xoa: ");
                    String name = scanner.nextLine();
                    boolean deleted = service.deleteContact(name);
                    if (deleted) {
                        System.out.println("Xoa lien he thanh cong.");
                    } else {
                        System.out.println("Khong tim thay lien he de xoa.");
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

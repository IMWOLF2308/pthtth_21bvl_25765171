package bai07;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            GeometryService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (GeometryService) registry.lookup("GeometryService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (GeometryService) registry.lookup("GeometryService");
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----- TINH DIEN TICH HINH HOC -----");
                System.out.println("1. Tinh dien tich hinh chu nhat");
                System.out.println("2. Tinh dien tich hinh tron");
                System.out.println("3. Tinh dien tich hinh tam giac");
                System.out.println("0. Thoat");
                System.out.print("Chon chuc nang: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Nhap chieu rong: ");
                    double width = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhap chieu dai: ");
                    double height = Double.parseDouble(scanner.nextLine());
                    double area = service.rectangleArea(width, height);
                    System.out.println("Dien tich hinh chu nhat: " + area);
                } else if (choice == 2) {
                    System.out.print("Nhap ban kinh: ");
                    double radius = Double.parseDouble(scanner.nextLine());
                    double area = service.circleArea(radius);
                    System.out.println("Dien tich hinh tron: " + area);
                } else if (choice == 3) {
                    System.out.print("Nhap do dai canh day: ");
                    double base = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhap chieu cao: ");
                    double height = Double.parseDouble(scanner.nextLine());
                    double area = service.triangleArea(base, height);
                    System.out.println("Dien tich hinh tam giac: " + area);
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

package bai08;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            FlightBookingService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (FlightBookingService) registry.lookup("FlightBookingService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (FlightBookingService) registry.lookup("FlightBookingService");
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----- DAT VE MAY BAY -----");
                System.out.println("1. Kiem tra so ghe trong");
                System.out.println("2. Dat ve");
                System.out.println("3. Huy ve");
                System.out.println("0. Thoat");
                System.out.print("Chon chuc nang: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.print("Nhap ma chuyen bay: ");
                    String flightNumber = scanner.nextLine();
                    int seats = service.availableSeats(flightNumber);
                    if (seats != -1) {
                        System.out.println("Chuyen bay " + flightNumber + " con trong " + seats + " ghe.");
                    } else {
                        System.out.println("Khong tim thay chuyen bay: " + flightNumber);
                    }
                } else if (choice == 2) {
                    System.out.print("Nhap ma chuyen bay: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Nhap so luong ve muon dat: ");
                    int seats = Integer.parseInt(scanner.nextLine());
                    boolean success = service.bookTicket(flightNumber, seats);
                    if (success) {
                        System.out.println("Dat ve thanh cong.");
                    } else {
                        System.out.println("Dat ve that bai. So ghe khong du hoac khong tim thay chuyen bay.");
                    }
                } else if (choice == 3) {
                    System.out.print("Nhap ma chuyen bay: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Nhap so luong ve muon huy: ");
                    int seats = Integer.parseInt(scanner.nextLine());
                    boolean success = service.cancelBooking(flightNumber, seats);
                    if (success) {
                        System.out.println("Huy ve thanh cong.");
                    } else {
                        System.out.println("Huy ve that bai. Ma chuyen bay khong hop le hoac so luong ve khong hop le.");
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

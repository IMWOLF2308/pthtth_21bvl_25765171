package bai09;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            AuctionService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (AuctionService) registry.lookup("AuctionService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (AuctionService) registry.lookup("AuctionService");
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("----- HE THONG DAU GIA TRUC TUYEN -----");
                System.out.println("1. Chu so huu: Dang san pham dau gia");
                System.out.println("2. Nguoi dau gia: Xem danh sach san pham");
                System.out.println("3. Nguoi dau gia: Dat gia thau");
                System.out.println("0. Thoat");
                System.out.print("Chon chuc nang: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice == 1) {
                    System.out.print("Nhap ten san pham: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Nhap gia khoi diem: ");
                    double startingPrice = Double.parseDouble(scanner.nextLine().trim());
                    boolean success = service.addProduct(name, startingPrice);
                    if (success) {
                        System.out.println("Dang san pham thanh cong.");
                    } else {
                        System.out.println("Dang san pham that bai. San pham da ton tai.");
                    }
                } else if (choice == 2) {
                    ArrayList<Product> products = service.getProductList();
                    if (products.isEmpty()) {
                        System.out.println("Hien tai chua co san pham nao trong phien dau gia.");
                    } else {
                        System.out.println("Danh sach san pham dau gia:");
                        for (int index = 0; index < products.size(); index++) {
                            System.out.println("[" + (index + 1) + "] " + products.get(index));
                        }
                    }
                } else if (choice == 3) {
                    System.out.print("Nhap ten nguoi dau gia: ");
                    String bidderName = scanner.nextLine().trim();
                    System.out.print("Nhap ten san pham can dat gia: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Nhap gia thau: ");
                    double bidAmount = Double.parseDouble(scanner.nextLine().trim());
                    boolean success = service.placeBid(name, bidAmount, bidderName);
                    if (success) {
                        System.out.println("Dat gia thau thanh cong.");
                    } else {
                        System.out.println("Dat gia that bai. Gia phai cao hon gia hien tai hoac khong tim thay san pham.");
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

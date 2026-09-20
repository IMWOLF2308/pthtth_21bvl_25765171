package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bai8_csvapp {
	public static void main(String[] args) {
		Path csvFile = Path.of("data", "inventory.csv");
		Path txtReport = Path.of("data", "inventory-report.txt");
		List<bai8_sanpham> inputbai8_sanphams = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		try {
			Files.createDirectories(csvFile.getParent());
		} catch (IOException e) {
			System.err.println("Không thể tạo thư mục: " + e.getMessage());
			return;
		}

		System.out.println(" Nhập Sản Phẩm");
		while (true) {
			try {
				System.out.print("Nhập mã sản phẩm (hoặc 'q' để dừng): ");
				String id = scanner.nextLine();
				if (id.equalsIgnoreCase("q"))
					break;

				System.out.print("Nhập tên sản phẩm: ");
				String name = scanner.nextLine();

				System.out.print("Nhập đơn giá: ");
				double price = Double.parseDouble(scanner.nextLine());

				System.out.print("Nhập số lượng: ");
				int quantity = Integer.parseInt(scanner.nextLine());

				bai8_sanpham p = new bai8_sanpham(id, name, price, quantity);
				inputbai8_sanphams.add(p);
				System.out.println("-> Đã thêm sản phẩm hợp lệ!\n");

			} catch (NumberFormatException e) {
				System.err.println("Lỗi: Giá hoặc số lượng phải là số hợp lệ. Vui lòng nhập lại!\n");
			} catch (IllegalArgumentException e) {
				System.err.println("Từ chối dữ liệu: " + e.getMessage() + " Vui lòng nhập lại!\n");
			}
		}

		boolean isFileExistsAndNotEmpty = false;
		try {
			isFileExistsAndNotEmpty = Files.exists(csvFile) && Files.size(csvFile) > 0;
		} catch (IOException e) {
			System.err.println("Lỗi kiểm tra tệp: " + e.getMessage());
		}

		try (BufferedWriter writer = Files.newBufferedWriter(csvFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
				StandardOpenOption.APPEND)) {

			if (!isFileExistsAndNotEmpty) {
				writer.write("Mã,Tên,Đơn giá,Số lượng");
				writer.newLine();
			}

			for (bai8_sanpham p : inputbai8_sanphams) {
				writer.write(p.toCSVString());
				writer.newLine();
			}
			System.out.println("\nĐã lưu danh sách vào: " + csvFile.toAbsolutePath());
		} catch (IOException e) {
			System.err.println("Lỗi ghi tệp CSV " + csvFile + ": " + e.getMessage());
		}

		List<bai8_sanpham> loadedbai8_sanphams = new ArrayList<>();
		System.out.println("\n đang đọc và tái tạo list sản phẩm");
		try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
			reader.readLine();
			String line;
			int lineNumber = 1;

			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (line.isBlank())
					continue;

				String[] parts = line.split(",", -1);
				if (parts.length != 4) {
					System.err.println("Bỏ qua dòng " + lineNumber + ": Thiếu/thừa cột dữ liệu.");
					continue;
				}

				try {
					bai8_sanpham p = new bai8_sanpham(parts[0].trim(), parts[1].trim(),
							Double.parseDouble(parts[2].trim()), Integer.parseInt(parts[3].trim()));
					loadedbai8_sanphams.add(p);
				} catch (Exception e) {
					System.err.println("Dòng " + lineNumber + " chứa dữ liệu không hợp lệ: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.err.println("Không tìm thấy hoặc không thể đọc tệp: " + csvFile.toAbsolutePath());
			return;
		}

		double totalInventoryValue = 0;
		bai8_sanpham maxbai8_sanpham = null;

		System.out.println("\n DANH SÁCH SẢN PHẨM");
		for (bai8_sanpham p : loadedbai8_sanphams) {
			System.out.println(p);
			totalInventoryValue += p.getInventoryValue();

			if (maxbai8_sanpham == null || p.getInventoryValue() > maxbai8_sanpham.getInventoryValue()) {
				maxbai8_sanpham = p;
			}
		}
		System.out.printf(">> Tổng giá trị tồn kho: %,.0f VND%n", totalInventoryValue);

		try (BufferedWriter writer = Files.newBufferedWriter(txtReport, StandardCharsets.UTF_8)) {
			writer.write("BÁO CÁO TỔNG HỢP TỒN KHO");
			writer.newLine();
			writer.write("==========================");
			writer.newLine();
			writer.write("Tổng số sản phẩm hợp lệ: " + loadedbai8_sanphams.size());
			writer.newLine();
			writer.write(String.format("Tổng giá trị tồn kho: %,.0f VND", totalInventoryValue));
			writer.newLine();
			if (maxbai8_sanpham != null) {
				writer.write("Sản phẩm có giá trị tồn kho cao nhất:");
				writer.newLine();
				writer.write("- " + maxbai8_sanpham.toString());
			}
			System.out.println("\nĐã xuất báo cáo tại: " + txtReport.toAbsolutePath());
		} catch (IOException e) {
			System.err.println("Lỗi ghi báo cáo " + txtReport + ": " + e.getMessage());
		}

		scanner.close();
	}
}
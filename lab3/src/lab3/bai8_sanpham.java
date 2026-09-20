package lab3;

public class bai8_sanpham {
	private String id;
	private String name;
	private double price;
	private int quantity;

	public bai8_sanpham(String id, String name, double price, int quantity) {
		if (id == null || id.trim().isEmpty()) {
			throw new IllegalArgumentException("Mã sản phẩm không được để trống.");
		}
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Tên sản phẩm không được để trống.");
		}
		if (price <= 0) {
			throw new IllegalArgumentException("Đơn giá phải lớn hơn 0.");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Số lượng không được âm.");
		}

		this.id = id.trim();
		this.name = name.trim();
		this.price = price;
		this.quantity = quantity;
	}

	public double getInventoryValue() {
		return this.price * this.quantity;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return String.format("Mã: %-5s | Tên: %-15s | Giá: %,.0f | SL: %-3d | Giá trị tồn: %,.0f", id, name, price,
				quantity, getInventoryValue());
	}

	public String toCSVString() {
		return String.format("%s,%s,%.0f,%d", id, name, price, quantity);
	}
}
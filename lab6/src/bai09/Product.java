package bai09;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double currentPrice;
    private String highestBidder;

    public Product(String name, double startingPrice) {
        this.name = name;
        this.currentPrice = startingPrice;
        this.highestBidder = "Chua co nguoi dau gia";
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    @Override
    public String toString() {
        return "San pham: " + name + "\n    Gia hien tai: " + (long) currentPrice + " VND\n    Nguoi giu gia cao nhat: " + highestBidder;
    }
}

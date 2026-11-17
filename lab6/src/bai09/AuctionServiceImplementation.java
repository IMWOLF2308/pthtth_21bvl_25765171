package bai09;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AuctionServiceImplementation extends UnicastRemoteObject implements AuctionService {
    private ArrayList<Product> productList;

    public AuctionServiceImplementation() throws RemoteException {
        super();
        productList = new ArrayList<Product>();
    }

    @Override
    public synchronized boolean addProduct(String name, double startingPrice) throws RemoteException {
        for (int index = 0; index < productList.size(); index++) {
            if (productList.get(index).getName().trim().equalsIgnoreCase(name.trim())) {
                return false;
            }
        }
        Product product = new Product(name.trim(), startingPrice);
        productList.add(product);
        System.out.println("Thong bao Server: San pham moi '" + name.trim() + "' voi gia khoi diem " + (long) startingPrice + " VND da duoc them vao he thong.");
        return true;
    }

    @Override
    public synchronized ArrayList<Product> getProductList() throws RemoteException {
        return productList;
    }

    @Override
    public synchronized boolean placeBid(String name, double bidAmount, String bidderName) throws RemoteException {
        for (int index = 0; index < productList.size(); index++) {
            Product product = productList.get(index);
            if (product.getName().trim().equalsIgnoreCase(name.trim())) {
                if (bidAmount > product.getCurrentPrice()) {
                    product.setCurrentPrice(bidAmount);
                    product.setHighestBidder(bidderName.trim());
                    System.out.println("Thong bao Server: " + bidderName.trim() + " da tra gia " + (long) bidAmount + " VND cho san pham '" + name.trim() + "' (gia cao nhat moi).");
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

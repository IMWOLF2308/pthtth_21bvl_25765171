package bai09;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AuctionService extends Remote {
    boolean addProduct(String name, double startingPrice) throws RemoteException;
    ArrayList<Product> getProductList() throws RemoteException;
    boolean placeBid(String name, double bidAmount, String bidderName) throws RemoteException;
}

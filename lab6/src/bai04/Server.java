package bai04;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static Registry registry;
    private static ContactService service;

    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (Exception exception) {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            }
            service = new ContactServiceImplementation();
            registry.rebind("ContactService", service);
            System.out.println("Server Bai 4 dang chay...");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

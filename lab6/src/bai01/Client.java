package bai01;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] arguments) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry;
            HelloService service;
            try {
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (HelloService) registry.lookup("HelloService");
            } catch (Exception exception) {
                Server.main(new String[0]);
                registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
                service = (HelloService) registry.lookup("HelloService");
            }
            String message = service.sayHello();
            System.out.println("Ket qua tu Server: " + message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}

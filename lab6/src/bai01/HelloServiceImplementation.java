package bai01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImplementation extends UnicastRemoteObject implements HelloService {

    public HelloServiceImplementation() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello, World!";
    }
}

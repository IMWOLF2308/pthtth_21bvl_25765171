package bai02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServiceImplementation extends UnicastRemoteObject implements CalculatorService {

    public CalculatorServiceImplementation() throws RemoteException {
        super();
    }

    @Override
    public int add(int firstNumber, int secondNumber) throws RemoteException {
        return firstNumber + secondNumber;
    }
}

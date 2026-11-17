package bai02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorService extends Remote {
    int add(int firstNumber, int secondNumber) throws RemoteException;
}

package bai06;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankServiceImplementation extends UnicastRemoteObject implements BankService {
    private double balance;

    public BankServiceImplementation(double initialBalance) throws RemoteException {
        super();
        this.balance = initialBalance;
    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    @Override
    public synchronized boolean withdraw(double amount) throws RemoteException {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            return true;
        }
        return false;
    }
}

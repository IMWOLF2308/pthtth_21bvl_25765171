package bai03;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeServiceImplementation extends UnicastRemoteObject implements PrimeService {

    public PrimeServiceImplementation() throws RemoteException {
        super();
    }

    @Override
    public boolean isPrime(int number) throws RemoteException {
        if (number < 2) {
            return false;
        }
        for (int index = 2; index <= Math.sqrt(number); index++) {
            if (number % index == 0) {
                return false;
            }
        }
        return true;
    }
}

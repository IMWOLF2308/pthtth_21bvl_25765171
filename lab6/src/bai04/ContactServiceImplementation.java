package bai04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactServiceImplementation extends UnicastRemoteObject implements ContactService {
    private HashMap<String, String> contactMap;

    public ContactServiceImplementation() throws RemoteException {
        super();
        contactMap = new HashMap<String, String>();
    }

    @Override
    public void addContact(String name, String phone) throws RemoteException {
        contactMap.put(name, phone);
    }

    @Override
    public String findContact(String name) throws RemoteException {
        return contactMap.get(name);
    }

    @Override
    public boolean deleteContact(String name) throws RemoteException {
        if (contactMap.containsKey(name)) {
            contactMap.remove(name);
            return true;
        }
        return false;
    }
}

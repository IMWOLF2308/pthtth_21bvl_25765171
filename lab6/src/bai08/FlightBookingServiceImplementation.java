package bai08;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class FlightBookingServiceImplementation extends UnicastRemoteObject implements FlightBookingService {
    private HashMap<String, Integer> flightMap;

    public FlightBookingServiceImplementation() throws RemoteException {
        super();
        flightMap = new HashMap<String, Integer>();
        flightMap.put("VN123", 50);
        flightMap.put("VN456", 30);
        flightMap.put("VJ789", 20);
    }

    @Override
    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        if (flightMap.containsKey(flightNumber)) {
            int currentSeats = flightMap.get(flightNumber);
            if (seats > 0 && seats <= currentSeats) {
                flightMap.put(flightNumber, currentSeats - seats);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        if (flightMap.containsKey(flightNumber)) {
            return flightMap.get(flightNumber);
        }
        return -1;
    }

    @Override
    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        if (flightMap.containsKey(flightNumber) && seats > 0) {
            int currentSeats = flightMap.get(flightNumber);
            flightMap.put(flightNumber, currentSeats + seats);
            return true;
        }
        return false;
    }
}

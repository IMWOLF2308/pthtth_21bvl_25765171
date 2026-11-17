package bai05;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatServiceImplementation extends UnicastRemoteObject implements ChatService {
    private String replyMessage;

    public ChatServiceImplementation() throws RemoteException {
        super();
        replyMessage = "";
    }

    @Override
    public synchronized void sendMessage(String message) throws RemoteException {
        replyMessage = "Server da nhan tin nhan: " + message;
    }

    @Override
    public synchronized String getReply() throws RemoteException {
        return replyMessage;
    }
}

package ktragiuaki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
          
            Socket socket = new Socket("localhost", 8888);
            
           
            ReadMessage readMessageThread = new ReadMessage(socket);
            Thread thread = new Thread(readMessageThread);
            thread.start();

         
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String input = scanner.nextLine();
                out.println(input);
                
                
                if (input.equals("/exit")) {
                    break;
                }
            }
            
           
            scanner.close();
            socket.close();
            
        } catch (IOException e) {
            System.out.println("Không thể kết nối tới Server. Hãy chắc chắn Server đang chạy.");
        }
    }
}


class ReadMessage implements Runnable {
    private Socket socket;
    private BufferedReader in;

    public ReadMessage(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String messageFromServer;
            
           
            while ((messageFromServer = in.readLine()) != null) {
                System.out.println(messageFromServer);
            }
        } catch (IOException e) {
            System.out.println("Kết nối tới Server đã bị đóng.");
        }
    }
}
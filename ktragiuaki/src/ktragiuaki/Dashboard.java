package ktragiuaki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Dashboard {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8889);
            System.out.println("Dashboard đang chạy. Chờ Server kết nối để nhận dữ liệu...\n");

            
            Socket socket = serverSocket.accept();
            System.out.println("Server đã kết nối tới Dashboard thành công!\n");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data;

            
            while ((data = in.readLine()) != null) {
               
                for (int i = 0; i < 5; i++) {
                    System.out.println();
                }
                
             
                String[] lines = data.split("\\|\\|");
                for (int i = 0; i < lines.length; i++) {
                    System.out.println(lines[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("Kết nối Dashboard bị ngắt: " + e.getMessage());
        }
    }
}
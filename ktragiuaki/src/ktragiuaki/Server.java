package ktragiuaki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static Map<String, List<ClientHandler>> rooms = new HashMap<>();
   
    private static PrintWriter dashboardOut = null; 

    public static void main(String[] args) {
        rooms.put("Study", new ArrayList<>());
        rooms.put("Game", new ArrayList<>());
        rooms.put("TraiNghiem", new ArrayList<>());

        
        try {
            Socket dashSocket = new Socket("localhost", 8889);
            dashboardOut = new PrintWriter(dashSocket.getOutputStream(), true);
            updateDashboard(); 
        } catch (IOException e) {
            System.out.println("Lưu ý: Không tìm thấy Dashboard. Bỏ qua tính năng thống kê.");
        }

        
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server đang chạy tại port 8888...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Lỗi Server: " + e.getMessage());
        }
    }

    
    public static void updateDashboard() {
        if (dashboardOut != null) {
            synchronized (rooms) {
                int totalClients = 0;
                StringBuilder sb = new StringBuilder();
                
                sb.append("========== BẢNG ĐIỀU KHIỂN SERVER ==========||");
                for (String roomName : rooms.keySet()) {
                    int count = rooms.get(roomName).size();
                    totalClients += count;
                    sb.append(" - Phòng [").append(roomName).append("]: ").append(count).append(" người||");
                }
                sb.append("--------------------------------------------||");
                sb.append(">> TỔNG SỐ PHÒNG : ").append(rooms.size()).append("||");
                sb.append(">> TỔNG SỐ CLIENT: ").append(totalClients).append("||");
                sb.append("============================================");
                
                dashboardOut.println(sb.toString()); 
            }
        }
    }

    public synchronized static void broadcastMessage(String message, String roomName, ClientHandler sender) {
        List<ClientHandler> clientsInRoom = rooms.get(roomName);
        if (clientsInRoom != null) {
            for (int i = 0; i < clientsInRoom.size(); i++) {
                ClientHandler client = clientsInRoom.get(i);
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientName;
        private String currentRoom;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.currentRoom = null;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Vui lòng nhập Tên của bạn:");
                clientName = in.readLine();
                out.println("Chào mừng " + clientName + " đã kết nối!");

                while (true) {
                    if (currentRoom == null) {
                        sendRoomList();
                        String roomChoice = in.readLine();
                        
                        if (roomChoice == null || roomChoice.equals("/exit")) {
                            break; 
                        }

                        synchronized (rooms) {
                            if (!rooms.containsKey(roomChoice)) {
                                rooms.put(roomChoice, new ArrayList<>());
                                out.println("Đã tạo phòng mới: " + roomChoice);
                                Server.updateDashboard(); 
                            }
                        }
                        joinRoom(roomChoice);
                        
                    } else {
                        String message = in.readLine();
                        
                        if (message == null || message.equals("/exit")) {
                            leaveRoom();
                            break; 
                        } else if (message.equals("/quit")) {
                            leaveRoom();
                        } else {
                            String formatMessage = clientName + ": " + message;
                            Server.broadcastMessage(formatMessage, currentRoom, this);
                        }
                    }
                }
            } catch (IOException e) {
                
            } finally {
                leaveRoom();
                try {
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendRoomList() {
            out.println("--- DANH SÁCH PHÒNG ---");
            for (String room : rooms.keySet()) {
                out.println("- " + room);
            }
            out.println("Nhập tên phòng để tham gia (hoặc tự nhập tên mới). /exit để thoát:");
        }

        private void joinRoom(String roomName) {
            currentRoom = roomName;
            synchronized (rooms) {
                rooms.get(currentRoom).add(this);
            }
            out.println("Bạn đã tham gia phòng: " + currentRoom);
            Server.broadcastMessage("--- " + clientName + " đã vào phòng ---", currentRoom, this);
            Server.updateDashboard(); 
        }

        private void leaveRoom() {
            if (currentRoom != null) {
                synchronized (rooms) {
                    List<ClientHandler> clients = rooms.get(currentRoom);
                    if (clients != null) {
                        clients.remove(this);
                    }
                }
                Server.broadcastMessage("--- " + clientName + " đã rời phòng ---", currentRoom, this);
                out.println("Bạn đã rời phòng " + currentRoom);
                currentRoom = null; 
                Server.updateDashboard(); 
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}

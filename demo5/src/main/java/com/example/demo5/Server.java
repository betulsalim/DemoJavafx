package com.example.demo5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static List<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started");

            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("New client connected");

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                new Thread(() -> {
                    while (true) {
                        try {
                            String message = dis.readUTF();
                            broadcast(message, socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String message, Socket socket) throws IOException {
        for (Socket s : sockets) {
            if (!s.equals(socket)) {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(message);
            }
        }
    }
}

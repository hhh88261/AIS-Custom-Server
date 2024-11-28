package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<Socket> clientSockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int socketPort = 9999;
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            System.out.println("포트번호 " + socketPort + "로 서버가 열렸습니다.");

            new Thread(() -> {
                while (true) {
                    try {
                        String aisMessage = encode.generateAisResult();
                        synchronized (clientSockets) {
                            for (Socket clientSocket : clientSockets) {
                                try {
                                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                                    writer.println(aisMessage);
                                } catch (IOException e) {
                                    try {
                                        clientSocket.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    clientSockets.remove(clientSocket);
                                }
                            }
                        }
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            while (true) {
                Socket socketUser = serverSocket.accept();
                System.out.println("Client가 접속함: " + socketUser.getLocalAddress());

                synchronized (clientSockets) {
                    clientSockets.add(socketUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

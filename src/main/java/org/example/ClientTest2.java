package org.example;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientTest2 {
    private  static encode encode = new encode();
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("155.155.20.20", 9999);
            System.out.println("접속 성공");
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // 서버로부터 응답 읽기
                String response;
                while ((response = reader.readLine()) != null) {
                    System.out.println(response);
                }
            }
        } catch (Exception e) {
            System.err.println("접속에 실패했습니다." + e.getMessage());
        }
    }
}


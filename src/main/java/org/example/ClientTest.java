package org.example;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientTest {
    private  static encode encode = new encode();
    public static void main(String[] args) {
        //Socket socket = null;
        try {
            //socket = new Socket();

            Socket socket = new Socket("155.155.20.20", 9999);
            System.out.println("접속 성공");

            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                // 서버에 데이터 전송
                writer.println("Client가 보내는 메시지 입니다.");

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

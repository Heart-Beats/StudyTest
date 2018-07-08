package com.HL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private ServerSocket serverSocket ;

    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        try {
            Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            StringBuilder sb = new StringBuilder();
            while (null != (msg = br.readLine())) {
                sb.append(msg);
                sb.append("\n");
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.start();
    }
}

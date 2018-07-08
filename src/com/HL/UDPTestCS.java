package com.HL;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPTestCS {

    public static class Server implements Runnable{

        private boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                try {
                    receiveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void receiveData() throws IOException {
            //创建服务器+指定端口
            DatagramSocket server = new DatagramSocket(8888);
            //准备接收容器
            byte[] container = new byte[1024];
            //封装成包
            DatagramPacket packet = new DatagramPacket(container, container.length);
            //接收数据
            server.receive(packet);
            //分析数据
            byte date[] = packet.getData();
            int length = packet.getLength();
            System.out.println(new String(date, 0, length));
            server.close();
        }

        public void stop() {
            flag = false;
        }

        public static void main(String[] args) {
            Server server = new Server();
            new Thread(server).start();
        }
    }

    public static class Client implements Runnable {

        private boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                try {
                    sendData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public void sendData() throws IOException {
            DatagramSocket client = new DatagramSocket(6666);
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.next();
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, new InetSocketAddress(
                    InetAddress.getLocalHost(), 8888));
            client.send(packet);
            client.close();
        }

        public void stop() {
            flag = false;
        }
        public static void main(String[] args) {
            Client client = new Client();
            new Thread(client).start();
        }
    }

}

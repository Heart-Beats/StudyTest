package com.HL.chatroom;

import HL.util.CloseStreamUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private static List<MyClient> clients = new ArrayList<>();
    private static Map<MyClient,String> myClientMap = new HashMap<>();
    private static String name;
    private static int num;

/*
    public Server(Socket socket) {
        this.socket = socket;
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            CloseStreamUtil.closeAllStream(bis,bos);
        }
    }*/

    static class MyClient implements Runnable {

        private BufferedInputStream bis = null;
        private BufferedOutputStream bos = null;
        private boolean isRunning = true;

        public MyClient(Socket socket) {
            try {
                bis = new BufferedInputStream(socket.getInputStream());
                bos = new BufferedOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                CloseStreamUtil.closeAllStream(bis, bos);
                isRunning = false;
            }
/*            switch (num) {
                case 0:
                    name = "小红";
                    break;
                case 1:
                    name = "小明";
                    break;
                case 2:
                    name = "小白";
                    break;
                default:
                    name = "未知用户";
            }*/
            clients.add(this);
          //  myClientMap.put(clients.get(num), name);
         //   num++;
        }

        private byte[] recevieData() throws IOException {
            byte temp[] = new byte[1024];
            bis.read(temp);
            return temp;

        }

        private void sendReceiveData(byte[] data) throws IOException {
            bos.write(data);
            bos.flush();
        }

        private void sendOtherClient(byte[] data) throws IOException {
            if (data == null) {
                return;
            }
            String clientName=myClientMap.get(this);
            for (MyClient client : clients) {
                if (client == this) {
                    continue;
                }
                String msg = clientName + ":" +new String(data).trim();
                client.sendReceiveData(msg.getBytes());
            }
        }

        @Override
        public void run() {
            while (isRunning) {
                try {
                    sendOtherClient(recevieData());
                } catch (IOException e) {
                    try {
                        System.out.println("客户端" + myClientMap.get(this)+ "已断开连接");
                        sendOtherClient("已离开聊天室".getBytes());
                    } catch (IOException e1) {
                    }
                    Server.clients.remove(this);
                    num--;
                    CloseStreamUtil.closeAllStream(bis, bos);
                    isRunning = false;
                }
            }
        }
    }

    public  void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                MyClient client = new MyClient(socket);
                client.sendReceiveData("欢迎进入聊天室：\n请输入用户名：".getBytes());
                name = new String(client.recevieData()).trim();
                System.out.println("客户端" + name + "已连接");
                myClientMap.put(clients.get(num++), name);
                client.sendOtherClient("已进入聊天室".getBytes());
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

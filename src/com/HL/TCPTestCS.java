package com.HL;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPTestCS {

    static class Server implements Runnable {

        private boolean flag = true;
        private static ServerSocket server = null;
        private Socket socket;

        public void stop(boolean flag) {
            this.flag = flag;
        }

        private static ServerSocket getServer() throws IOException {
            //  if (null == server) {
            //    synchronized (Client.class) {
            if (null == server) {
                server = new ServerSocket(8888);
            }
            //    }
            //   }
            return server;
        }

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


        private void sendData() throws IOException {
            // if (null==socket) {
            socket = getServer().accept();
            System.out.println("一个客户端已连接！");
            //  }
            Scanner scanner = new Scanner(System.in);
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            while (true) {
                String msg = scanner.nextLine();
                byte data[] = msg.getBytes();
                bos.write(data);
                //bos.newLine();
                bos.flush();
            }

        }

        public static void main(String[] args) {
            Server server = new Server();
            //new Thread(server).start();
            try {
                server.sendData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Client implements Runnable {

        private boolean flag = true;
        BufferedInputStream bis;

        public Client(Socket client) {
            try {
                bis = new BufferedInputStream(client.getInputStream());
            } catch (IOException e) {
                //e.printStackTrace();
                flag = false;
            }
        }


        public void stop(boolean flag) {
            this.flag = flag;
        }

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

        private void receiveData() throws IOException {
            byte[] data = new byte[1024];
            int len = bis.read(data);
            String str=  new String(data, 0, len);
            //String str = null;
            // while (null != (str = bis.readLine())) {
            // str = bis.readLine();

            //}
            System.out.println(str);
        }

        public static void main(String[] args) {
            Socket client;
            try {
                client = new Socket(InetAddress.getLocalHost(), 8888);
                System.out.println("连接服务器成功！");
                new Thread(new Client(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
/*            try {
                client.receiveData();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }


}

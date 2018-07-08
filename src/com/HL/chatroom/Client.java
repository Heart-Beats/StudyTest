package com.HL.chatroom;

import HL.util.CloseStreamUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private BufferedInputStream bis=null;
    private BufferedOutputStream bos=null;

    public Client(Socket socket) {
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            CloseStreamUtil.closeAllStream(bis,bos);
        }
    }


    private Thread sendThread = new Thread(() -> {
        while (true) {
            try {
                //Thread.sleep(200);
                sendData();
               // Thread.sleep(200);
            } catch (IOException e) {
                CloseStreamUtil.closeAllStream(bos);
                break;
            }
        }
    });

    private Thread receiveThread = new Thread(() -> {
        while (true) {
            try {
                receiveData();
            } catch (IOException e) {
                CloseStreamUtil.closeAllStream(bis);
                break;
            }
        }
    });

    private void receiveData() throws IOException {
        byte temp[] = new byte[1024];
        int len = bis.read(temp);
        System.out.println(new String(temp, 0, len));
    }

    private void sendData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       // System.out.print("你:");
        String msg = br.readLine();
        bos.write(msg.getBytes());
        bos.flush();
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 8888);
            Client client = new Client(socket);
            client.receiveThread.start();
            client.sendThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

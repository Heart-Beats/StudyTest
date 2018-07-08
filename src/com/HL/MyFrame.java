package com.HL;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends Frame {

    private int width;
    private int height;

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    static void lauchFrame() {
        MyFrame frame = new MyFrame();
        frame.setWidth(400);
        frame.setHeight(400);
        frame.setBounds((1366 - frame.getWidth()) / 2, (768 - frame.getHeight()) / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Runnable runnable = frame.new MyThread();
        Thread thread = new Thread(runnable);
        thread.start();
    }

    double degree = 0;

    @Override
    public void paint(Graphics g) {
        int width = 300;
        int height = 300;
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2 + 30;
        g.drawRect(x, y, width, height);
        g.drawOval(x, y, width, height);
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.setColor(Color.green);
        g.drawLine(x, centerY, x + width, centerY);
        g.setColor(Color.cyan);
        g.drawLine(centerX, y, centerX, y + height);
        g.setColor(Color.magenta);

        //     while (degree <= 2 * Math.PI) {
        double x1 = 100 * Math.cos(degree) + centerX - 5;
        double y1 = 100 * Math.sin(degree) + centerY - 5;
        g.fillOval((int) x1, (int) y1, 10, 10);
/*            if (degree == 0 || degree == Math.PI) {
                System.out.println(Math.abs(centerX - x1));
            } else if (degree == Math.PI / 2 || degree == Math.PI * 3 / 2) {
                System.out.println(Math.abs(centerY - y1));
            }*/
        degree += Math.PI / 180;
        //    }
        g.fillOval(centerX - 50 / 2, centerY - 50 / 2, 50, 50);
        g.setColor(Color.gray);
        g.setFont(new Font("楷体", Font.BOLD, 20));
        g.drawString("这是我画的图", x + 30, y - 20);

        g.draw3DRect(x, y, width, height, false);
        g.drawArc(x + 20, y + 20, 100, 100, 20, 50);

    }

    void drawCustomCircle(double a, double b) { //a等于b时为圆
        Graphics g = getGraphics();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 30;
        int x = (int) (a * Math.cos(degree) + centerX) - 5;
        int y = (int) (b * Math.sin(degree) + centerY) - 5;
        g.setColor(Color.cyan);
        g.fillOval(x, y, 10, 10);
/*        int x1 = (int) ((int) 1*((2*Math.cos(degree)-Math.cos(2*degree))*degree)+centerX);  //心❤线
        int y1 = (int) ((int) 1*((2*Math.sin(degree)-Math.sin(2*degree))*degree)+centerY);
        System.out.println(""+ x1+"\t"+y1);
        g.fillOval(x1, y1, 5, 5);*/
        degree -= Math.PI / 180;
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                drawCustomCircle(100, 50);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        lauchFrame();
    }
}

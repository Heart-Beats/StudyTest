package com.HL;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 淡然爱汝不离
 */
public class RedGreenLight {
    private String lightColor;
    private Boolean flag = false;
    private long spaceTime;

    public RedGreenLight(long spaceTime) {
        this.spaceTime = spaceTime;
    }

    private void redLight() {
        lightColor = "红灯";
        flag = false;
    }

    private void greenLight() {
        lightColor = "绿灯";
        flag = true;
    }

    private synchronized void passerByWatchLight() throws InterruptedException {
 //       synchronized (flag) {
            if (!flag) {
                this.wait();
            }
            System.out.println("绿灯路人行！");
            Thread.sleep(spaceTime);
            this.notify();
            //flag = false;
    //    }
    }

    private synchronized void driverWatchLight() throws InterruptedException {
  //      synchronized (flag) {
            if (flag) {
                this.wait();
            }
            System.out.println("红灯司机行！");
            Thread.sleep(spaceTime);
            this.notify();
          //  flag = true;
   //     }

    }

    public  void changeLight() {
       // synchronized (flag) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    while (!flag) {
                        greenLight();
                        System.out.println(lightColor);
                        try {
                            Thread.sleep(spaceTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        redLight();
                        System.out.println(lightColor);
                        try {
                            Thread.sleep(spaceTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Date(System.currentTimeMillis()), 2*spaceTime);
      //  }

    }


   public class PasserBy implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    passerByWatchLight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
/*

       private synchronized void watchLight() throws InterruptedException {

           if (!flag) {
               this.wait();
           }
           System.out.println("绿灯路人行！");
           Thread.sleep(10 * 1000);
           this.notifyAll();
           flag = false;
       }
*/

    }

   public class Driver implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    driverWatchLight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
/*       private synchronized void watchLight() throws InterruptedException {
               if (flag) {
                   this.wait();
               }
               System.out.println("红灯司机行!");
               Thread.sleep(10*1000);
               this.notifyAll();
                flag=true;
        }*/
    }
}

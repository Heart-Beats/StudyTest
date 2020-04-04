package com.HL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 淡然爱汝不离 At 2018/10/21
 */
public class TrafficSignal {

    interface Light {

        /**  改变灯的颜色
         * @param color  设置灯的颜色
         */
        void setColor(String color);
    }

    public class Light1 implements Light,Runnable {
        private String lightColor;
        private boolean running = true;
        private long lightTimes;

        private List<Light2> greenLights;

        public Light1(long lightTimes) {
            lightColor = "红灯";
            this.lightTimes = lightTimes*1000;
        }

        public void addGreenLight(Light2 greenLight) {
            if (greenLights == null) {
                greenLights = new ArrayList<>();
            }
            greenLight.setLight1(this);
            greenLights.add(greenLight);
        }

        public void removeGreenLight(Light2 greenLight) {
            if (greenLights.contains(greenLight)) {
                greenLights.remove(greenLight);
            }
        }

        @Override
        public void setColor(String color) {
            lightColor = color;
            String greenLightColor = "红灯".equals(color) ? "绿灯" : "红灯";
            notifyColorChange(greenLightColor);
        }

        private void notifyColorChange(String greenLightColor) {
            for (Light2 greenLight : greenLights) {
                greenLight.setColor(greenLightColor);
            }
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public  void run() {
                while (running) {
                    synchronized (greenLights.get(0)) {
                    System.out.println("Light1的颜色:" + lightColor);
                    try {
                        for (long times = lightTimes/1000; times > 0; times--) {
                            Thread.sleep(1000);
                            System.out.println(times);
                        }
                        System.out.println("改变灯色");
                        setColor("绿灯");
                        System.out.println("Light1的颜色:" + lightColor);
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class Light2 implements Light, Runnable {
        private String lightColor = "绿灯";
        private String lightName;

        private Light1 light1;

        public void setLight1(Light1 light1) {
            this.light1 = light1;
        }

        public Light2(String lightName) {
            this.lightName = lightName;
        }

        @Override
        public void setColor(String color) {
            lightColor = color;
        }

        public String getLightColor() {
            System.out.println(lightName + ":" + lightColor);
            return lightColor;
        }

        @Override
        public  void run() {
           synchronized (light1){
                while (true) {
                    String color = lightColor;
                    if (!lightColor.equals(color)) {
                        getLightColor();
                        try {
                            Thread.sleep(light1.lightTimes);
                            for (long times =light1.lightTimes/1000; times > 0; times--) {
                                System.out.println(times);
                            }
                            light1.setColor("红灯");
                            light1.notify();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        TrafficSignal trafficSignal = new TrafficSignal();
        Light1 light1 = trafficSignal.new Light1(5);
        Light2 light2 = trafficSignal.new Light2("1号灯");
        //Light2 light3 = trafficSignal.new Light2("2号灯");
        light1.addGreenLight(light2);
        //light1.addGreenLight(light3);
        new Thread(light1).start();
        new Thread(light2).start();
        // new Thread(light3).start();
    }
}

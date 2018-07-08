package com.HL;

public class CarFactory implements Factory {

    public static void createCar() {
        System.out.println("宝马");
    }

    public static void main(String[] args) {

    }
}

class LuxCarFactory extends CarFactory {
    public static void main(String[] args) {
        LuxCarFactory.createCar();
    }
}
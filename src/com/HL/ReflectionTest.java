package com.HL;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionTest {

    private String name;
    private int age;

    public ReflectionTest(int age) {
        this.age = age;
    }

    public ReflectionTest(String name, int age) {
        this(age);
        this.name = name;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class c = Class.forName("com.HL.ReflectionTest");
        Constructor<ReflectionTest> con = c.getConstructor(int.class);
        ReflectionTest t =  con.newInstance(22);
        System.out.println(c.getDeclaredField("age").get(t));

    }

}

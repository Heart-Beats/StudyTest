package com.HL;

import java.lang.reflect.Type;

public class Face {
    String type;

    Face() {

    }

    Face(String type) {
        new Face().new Eey().see();
        this.type = type;
    }

    class Eey {
        String eyeColor;

        void see() {
            System.out.println("脸型：" + type);
            System.out.println("眼睛颜色:" + eyeColor );
            System.out.println("用眼睛看");
        }
    }

    static class Ear {
          static String earType;

         static void listen() {
            System.out.println("耳朵类型：" + earType);
            System.out.println("用耳朵听");
        }
    }

    static void test(Galass galass) {
        galass.blind();
    }
    public static void main(String args[]) {
        Face.Eey eye = new Face("瓜子脸").new Eey();
        eye.eyeColor = "黑色";
        eye.see();
        Ear.earType = "顺风耳";
        Ear.listen();
        test(new Galass() {
            @Override
            public void blind() {
                System.out.println("带了太阳镜");
            }
        });
    }
}

interface Galass {
    void blind();
}

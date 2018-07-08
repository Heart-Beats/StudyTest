package HL.util;

import java.util.Arrays;

public class ArrayUtil {

    /**
     * 一维数组简单版冒泡排序，不考虑任何优化
     *
     * @param value value：需要排序的一维数组
     * @return
     */
    public static int[] ppSort(int value[]) {
        for (int j = 0; j < value.length - 1; j++) { //趟数
            for (int i = 0; i < value.length - 1; i++) { //次数
                if (value[i] > value[i + 1]) {
                    int temp = value[i];
                    value[i] = value[i + 1];
                    value[i + 1] = temp;
                }
            }
        }
        return value;
    }

    /**
     * 一维数组优化版冒泡排序，减少每趟的次数
     *
     * @param value value：需要排序的一维数组
     * @return
     */
    public static int[] ppSort2(int value[]) {
        for (int j = 0; j < value.length - 1; j++) { //趟数
            for (int i = 0; i < value.length - 1 - j; i++) { //次数
                if (value[i] > value[i + 1]) {
                    int temp = value[i];
                    value[i] = value[i + 1];
                    value[i + 1] = temp;
                }
            }
        }
        return value;
    }

    /**
     * 一维数组最终版版冒泡排序，假定某趟的已经有序
     *
     * @param value value：需要排序的一维数组
     * @return
     */
    public static int[] ppSort3(int value[]) {
        boolean sorted = true;
        for (int j = 0; j < value.length - 1; j++) { //趟数
            sorted = true;
            for (int i = 0; i < value.length - 1 - j; i++) { //次数
                if (value[i] > value[i + 1]) {
                    int temp = value[i];
                    value[i] = value[i + 1];
                    value[i + 1] = temp;
                    sorted = false;
                }
                if (sorted) {
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 二维数组冒泡排序
     *
     * @param value value是需要排序的数组
     * @return 返回排序后的数组
     */
    public static int[][] ppSort(int value[][]) {
        for (int k = 0; k < value.length; k++)
            for (int m = 0; m < value[k].length; m++)
                for (int i = 0; i < value.length; i++)
                    for (int j = 0; j < value[i].length; j++) {
                        final int flag = value[i].length - 1;
                        if (j < flag) {
                            if (value[i][j] > value[i][j + 1]) {
                                int temp = value[i][j];
                                value[i][j] = value[i][j + 1];
                                value[i][j + 1] = temp;
                            }
                        } else if (j == flag && i < value.length - 1) {
                            if (value[i][j] > value[i + 1][0]) {
                                int temp = value[i][j];
                                value[i][j] = value[i + 1][0];
                                value[i + 1][0] = temp;
                            }
                        }
                    }
        return value;
    }

    /**
     * 将二维数组的值放入一维数组，把一位数组冒泡排序，再转换成二维数组
     *
     * @param value value是需要排序的数组
     * @return 返回排序后的数组
     */
    public static int[][] ppSort2(int value[][]) {
        int temp[] = null;
        int tempLength = 0;
        int m = 0;
        for (int i = 0; i < value.length; i++) {
            tempLength += value[i].length;
            if (i == value.length - 1) {
                temp = new int[tempLength];
                for (int[] ints : value) {//使用该方法遍历，ints和anInt都指向数组的值，相当于引用，无法修改数组中的值
                    for (int anInt : ints) {
                        temp[m] = anInt; //遍历出二维数组的每个值给一维数组，不需要循环一维数组
                        m++;
                    }
                }
            }
        }
        if (temp != null) {
            temp = ArrayUtil.ppSort3(temp);
            m = 0;
            for (int j = 0; j < value.length; j++) {
                for (int k = 0; k < value[j].length; k++) {
                    value[j][k] = temp[m];//将一维数组的每个值给遍历出二位数组的每个值，不需要循环一维数组
                    m++;
                }
            }
        }
        return value;
    }


    public static void main(String args[]) {
        int a[] = {9, 8, 7, 6, 5, 4, 3, 2, 1,};
        int b[][] = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        int c[][][] = {b, b, b};
        System.out.println(Arrays.toString(ppSort2(a)));
        for (int[] ints : ppSort(b)) {
            System.out.println(Arrays.toString(ints));
        }

    }
}

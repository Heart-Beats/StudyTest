package HL.util;

import java.util.Objects;

public class MyArrayList {

    private Object value[];
    private int size;

    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int capacity) {
        value = new Object[capacity];
    }

    public void add(Object object) {
        value[size++] = object;
        if (size == value.length) {
            int newCapacity = value.length * 2 + 2;
            Object newValue[] = new Object[newCapacity];
            for (int i = 0; i < value.length; i++) {
                newValue[i] = value[i];
            }
            value = newValue;
        }
    }

    public Object get(int index) {
        Objects.checkIndex(index, size);
        return value[index];
    }

    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (value[i] == null)
                    return i;
            }
        }
        for (int i = 0; i < size; i++) {
            if (value[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public int getSize() {
        return size;
    }

    public void set(int index, Object object) {
        Objects.checkIndex(index, size);
        value[index] = object;
    }


    public void remove(Object object) {
        boolean canfind = false;
        for (int i = 0; i < size; i++) {
            if (value[i].equals(object)) {
                canfind = true;
/*                while (i < size) { //删除数组中某一元素，遍历挨个移位
                    value[i] = value[i + 1];
                    i++;
                }*/
                System.arraycopy(value, i + 1, value, i, value.length - i - 1);
                break;
            }
        }
        if (!canfind) {
//            System.out.println("没有此对象");
            try {
                throw new Exception("没有此对象");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            size--;
        }
    }
}

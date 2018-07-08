package HL.util;

import java.util.Iterator;

public class MyHashSet {
    private MyHashMap map;

    private final Object object;

    public MyHashSet() {
        map = new MyHashMap();
        object = new Object();
    }

    public int size() {
        return map.size();
    }

    public void add(Object object) {
        map.put(object,this.object);
    }

    public static void main(String[] args) {
        MyHashSet myHashSet = new MyHashSet();
        myHashSet.add("张磊");
        myHashSet.add("很爱");
        myHashSet.add("丫头");
        System.out.println(myHashSet.size());
    }

    class myIterator implements Iterator {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }

}

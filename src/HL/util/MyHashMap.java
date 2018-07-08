package HL.util;

public class MyHashMap {
    private MyLinkedList mapList[] ;

    class Entry {
        Object key;
        Object value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private int size;

    public int size() {
        return size;
    }

    public MyHashMap() {  //HashMap不需要扩容，因为数组的每个元素为链表，当indexHash相同时，会在链表后面添加
        this(16);
    }

    public MyHashMap(int capacity) {
        mapList = new MyLinkedList[capacity];
    }

    public void put(Object key, Object value) {
        Entry e = new Entry(key, value);
        int indexHash = key.hashCode() & (mapList.length - 1);
        MyLinkedList linkedList = mapList[indexHash];
        if (linkedList == null) {
            linkedList = new MyLinkedList();
            mapList[indexHash] = linkedList;
            linkedList.add(e);
            size++;
        } else {
            boolean b =false;
            for (int i = 0; i<linkedList.size();i++) {
                Entry entry = (Entry) linkedList.get(i);
                if (entry.key.equals(key)) {
                    b= true;
                    entry.value = value;
                }
            }
            if (!b) {
                linkedList.add(e);
                size++;
            }
        }
    }

    public Object get(Object key) {//从计算的索引处得到链表，再遍历链表中的每一个元素（key，value），找到指定key的元素返回value
        int indexHash = key.hashCode() & (mapList.length - 1);
        MyLinkedList linkedList = mapList[indexHash];
        for (int i = 0; i<linkedList.size();i++) {
            Entry e = (Entry) linkedList.get(i);
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        MyHashMap hashMap = new MyHashMap();
        hashMap.put("我最爱的人", "丫头");
        hashMap.put("我最爱的人", "张欢");
        hashMap.put(123, "妈妈");
        System.out.println(hashMap.get("我最爱的人"));
        System.out.println(hashMap.get(123));
    }
}

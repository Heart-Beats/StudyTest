package HL.util;


import java.util.Objects;

public class MyLinkedList {

    private class Node {
        Node previous;
        Object obj;
        Node next;
    }

    private int size;
    private Node firstNode;
    private Node lastNode;

    public void add(Object obj) {
        Node n = new Node();
        if (firstNode == null) {
            n.previous = null;
            n.obj = obj;
            n.next = null;
            firstNode = n;
            lastNode = n;
        } else {
            lastNode.next = n;
            n.previous = lastNode;
            n.obj = obj;
            n.next = null;
            lastNode = n;
        }
        size++;
    }

    public void add(int index, Object obj) {
        Node temp = getINdexNode(index);
        Node preNode = temp.previous;
        Node n = new Node();
        n.obj = obj;
        if (null == temp.previous) {
            firstNode = n;
            n.next = temp;
            temp.previous = n;
        } else {
            preNode.next = n;
            n.previous = preNode;
            temp.previous = n;
            n.next = temp;
        }
        size++;
    }

    public Object get(int index) {
        Node temp = getINdexNode(index);
        return temp.obj;
    }

    private Node getINdexNode(int index) {//获得指定索引的节点
        Objects.checkIndex(index, size);
        Node temp = null;
        if (index < (size >> 2)) {
            temp = firstNode;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = lastNode;
            for (int i = size - 1; i > index; i--) {
                temp = temp.previous;
            }
        }
        return temp;
    }

    public void remove(int index) {
        Node temp = getINdexNode(index);
        deleteNode(temp);
        size--;
    }

    private void deleteNode(Node temp) {
        Node preNode = temp.previous;
        Node nextNode = temp.next;
        if (null == temp.previous) {
            firstNode = nextNode;
            firstNode.previous = null;
        } else if (null == temp.next) {
            lastNode = preNode;
            lastNode.next = null;
        } else {
            preNode.next = nextNode;
            nextNode.previous = preNode;
        }
    }

    public void remove(Object obj) {
        Node temp = firstNode;
        for (int i = 0; i < size; i++) {
            if (temp.obj == obj) {
                deleteNode(temp);
            } else {
                temp = temp.next;
            }
            if (i > size) {
                try {
                    throw new Exception("没有找到该对象！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        size--;
    }

    public void set(int index, Object obj) {
        Node temp = getINdexNode(index);
        temp.obj = obj;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.add("张磊");
        linkedList.add("很爱");
        linkedList.add("丫头");
        linkedList.remove("张磊");
        linkedList.add(0, "张磊");
        linkedList.set(1, "只爱");
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }
        //     System.out.println(linkedList.get(3));

    }

}

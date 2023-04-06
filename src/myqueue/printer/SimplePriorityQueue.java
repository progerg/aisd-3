package myqueue.printer;

import java.util.Comparator;

public class SimplePriorityQueue<T> {

    private Node head;
    private Node tail;
    private int size;
    private Comparator<T> comparator;

    private class Node {
        T item;
        Node next;

        public Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    public SimplePriorityQueue(Comparator<T> comparator) {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.comparator = comparator;
    }

    public void add(T item) {
        Node newNode = new Node(item);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (comparator.compare(item, head.item) < 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null && comparator.compare(item, current.next.item) >= 0) {
                current = current.next;
            }

            newNode.next = current.next;
            current.next = newNode;

            if (newNode.next == null) {
                tail = newNode;
            }
        }

        size++;
    }

    public T poll() {
        if (head == null) {
            return null;
        }

        T item = head.item;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        }

        return item;
    }

    public T peek() {
        if (head == null) {
            return null;
        }

        return head.item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public boolean contains(T item) {
        Node current = head;
        while (current != null) {
            if (item.equals(current.item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

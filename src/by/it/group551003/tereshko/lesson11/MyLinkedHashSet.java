package by.it.group551003.tereshko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> orderNext;
        Node<E> orderPrev;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private Node<E> head;
    private Node<E> tail;

    public MyLinkedHashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

    private int hash(Object key, int length) {
        if (key == null) return 0;
        return Math.abs(key.hashCode() % length);
    }

    private void resize() {
        if (size >= table.length * 3 / 4) {
            Node<E>[] newTable = (Node<E>[]) new Node[table.length * 2];
            for (Node<E> eNode : table) {
                Node<E> node = eNode;
                while (node != null) {
                    Node<E> next = node.next;
                    int index = hash(node.data, newTable.length);
                    node.next = newTable[index];
                    newTable[index] = node;
                    node = next;
                }
            }
            table = newTable;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<E> current = head;
        boolean first = true;
        while (current != null) {
            if (!first)
                sb.append(", ");
            Object element = current.data;
            sb.append(element == this ? "(this Collection)" : String.valueOf(element));
            first = false;
            current = current.orderNext;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean contains(Object o) {
        int index = hash(o, table.length);
        Node<E> node = table[index];
        while (node != null) {
            if (node.data == o || (node.data != null && node.data.equals(o)))
                return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (contains(e))
            return false;
        resize();
        int index = hash(e, table.length);
        Node<E> newNode = new Node<>(e);
        newNode.next = table[index];
        table[index] = newNode;
        if (tail == null)
            head = tail = newNode;
        else {
            tail.orderNext = newNode;
            newNode.orderPrev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = hash(o, table.length);
        Node<E> node = table[index];
        Node<E> prev = null;
        while (node != null) {
            if (node.data == o || (node.data != null && node.data.equals(o))) {
                if (prev == null)
                    table[index] = node.next;
                else
                    prev.next = node.next;

                if (node.orderPrev != null)
                    node.orderPrev.orderNext = node.orderNext;
                else
                    head = node.orderNext;

                if (node.orderNext != null)
                    node.orderNext.orderPrev = node.orderPrev;
                else
                    tail = node.orderPrev;
                node.next = null;
                node.orderNext = null;
                node.orderPrev = null;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e)) modified = true;
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> current = head;
        while (current != null) {
            Node<E> next = current.orderNext;
            if (!c.contains(current.data)) {
                remove(current.data);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c)
            if (remove(e)) modified = true;
        return modified;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        head = null;
        tail = null;
        size = 0;
    }
}

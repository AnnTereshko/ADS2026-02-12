package by.it.group551003.tereshko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public MyHashSet() {
        table = (Node<E>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private int hash(Object key, int length) {
        if (key == null)
            return 0;
        return Math.abs(key.hashCode() % length);
    }

    private void resize() {
        if (size >= table.length * 3 / 4) {
            Node<E>[] oldTable = table;
            Node<E>[] newTable = (Node<E>[]) new Node[oldTable.length * 2];
            for (Node<E> eNode : oldTable) {
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

    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            Node<E> current = table[i];
            while (current != null) {
                if (!first)
                    sb.append(", ");
                Object element = current.data;
                sb.append(element == this ? "(this Collection)" : String.valueOf(element));
                first = false;
                current = current.next;
            }
        }

        sb.append(']');
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        size = 0;
    }
}

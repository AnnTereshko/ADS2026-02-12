package by.it.group551003.tereshko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

//    toString()
//    add(E element)
//    remove(int)
//    remove(E element)
//    size()
//
//    addFirst(E element)
//    addLast(E element)
//
//    element()
//    getFirst()
//    getLast()
//
//    poll()
//    pollFirst()
//    pollLast()

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E item, Node<E> nextNode) {
            this.data = item;
            this.next = nextNode;
        }
    }

    private Node<E> first;
    private Node<E> last;
    private int size;

    public String toString() {
        if (size == 0) return "[]";
        String result = "[";
        Node<E> current = first;
        for (int i = 0; i < size; i++) {
            result += current.data;
            if (i < size - 1)
                result += ", ";
            current = current.next;
        }
        result += "]";
        return result;
    }

    @Override
    public void addFirst(E e) {
        if (e == null) throw new NullPointerException("");
        Node<E> newNode = new Node<>(e, first);
        first = newNode;
        if (last == null)
            last = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (e == null) throw new NullPointerException("");
        Node<E> newNode = new Node<>(e, null);
        if (last == null)
            last = first = newNode;
        else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        if (first == null) return null;
        E data = first.data;
        first = first.next;
        if (first == null)
            last = null;
        size--;
        return data;
    }

    @Override
    public E pollLast() {
        if (last == null) return null;
        E data = last.data;
        if (first == last) {
            first = null;
            last = null;
        } else {
            Node<E> current = first;
            while (current.next != last)
                current = current.next;
            current.next = null;
            last = current;
        }
        size--;
        return data;
    }

    @Override
    public E getFirst() {
        if (first == null) throw new NoSuchElementException("Deque is empty!!!");
        return first.data;
    }

    @Override
    public E getLast() {
        if (last == null) throw new NoSuchElementException("Deque is empty");
        return last.data;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) return pollFirst();
        Node<E> current = first;
        for (int i = 0; i < index - 1; i++)
            current = current.next;
        E data = current.next.data;
        current.next = current.next.next;
        if (current.next == null) last = current;
        size--;
        return data;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || first == null) return false;
        if (o.equals(first.data)) {
            pollFirst();
            return true;
        }
        Node<E> current = first;
        while (current.next != null) {
            if (o.equals(current.next.data)) {
                current.next = current.next.next;
                if (current.next == null) last = current;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
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
    public Iterator<E> descendingIterator() {
        return null;
    }
}

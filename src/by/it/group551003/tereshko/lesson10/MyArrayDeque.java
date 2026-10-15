package by.it.group551003.tereshko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E> implements Deque<E> {

    private E[] elements;
    private int head;
    private int tail;
    private int size;
    private int capacity;

    public MyArrayDeque() {
        this(10);
    }

    public MyArrayDeque(int firstCapacity) {
        if (firstCapacity <= 0) throw new IllegalArgumentException("Capacity must be positive!");
        this.elements = (E[]) new Object[firstCapacity];
        this.size = 0;
        this.capacity = firstCapacity;
        this.head = 0;
        this.tail = 0;
    }
    //                toString()
    //                size()
    //
    //                add(E element)
    //                addFirst(E element)
    //                addLast(E element)
    //
    //                element()
    //                getFirst()
    //                getLast()
    //
    //                poll()
    //                pollFirst()
    //                pollLast()

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(elements[(head + i) % capacity]);
            if (i < size - 1)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }

    private void resize() {
        int newCapacity = capacity * 2;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++)
            newElements[i] = elements[(head + i) % capacity];
        elements = newElements;
        head = 0;
        tail = size;
        capacity = newCapacity;
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
    public boolean add(E element) {
        addLast(element);
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

    @Override
    public void addFirst(E element) {
        if (element == null) throw new NullPointerException("Null elements are not allowed!!!");
        if (size == capacity)
            resize();
        head = (head - 1 + capacity) % capacity;
        elements[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (element == null) throw new NullPointerException("Null elements are not allowed!!!");
        if (size == capacity) {
            resize();
        }
        elements[tail] = element;
        tail = (tail + 1) % capacity;
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
    public E element() {
        if (size == 0) throw new NoSuchElementException("Deque is empty!!!");
        return elements[head];
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
    public boolean remove(Object o) {
        return false;
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
    public E getFirst() {
        return element();
    }

    @Override
    public E getLast() {
        if (size == 0) throw new NoSuchElementException("Deque is empty!!!");
        return elements[(tail - 1 + capacity) % capacity];
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
    public E poll() {
        if (size == 0) return null;
        E result = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        size--;
        return result;
    }

    @Override
    public E pollFirst() {
        return poll();
    }

    @Override
    public E pollLast() {
        if (size == 0) return null;
        tail = (tail - 1 + capacity) % capacity;
        E result = elements[tail];
        elements[tail] = null;
        size--;
        return result;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}

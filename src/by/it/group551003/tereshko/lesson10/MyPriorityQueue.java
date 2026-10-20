package by.it.group551003.tereshko.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E> implements Queue<E> {

    private E[] heap;
    private int size;
    private int capacity;

    public MyPriorityQueue() {
        this.capacity = 11;
        this.heap = (E[]) new Comparable[this.capacity];
        this.size = 0;
    }

    ///                 toString()
    ///                 size()
    ///                 clear()
    ///                 add(E element)
    ///                 remove()
    ///                 contains(E element)
    ///
    ///                 offer(E element)
    ///                 poll()
    ///                 peek()
    ///                 element()
    ///                 isEmpty()
    ///
    ///                 containsAll(Collection<E> c)
    ///                 addAll(Collection<E> c)
    ///                 removeAll(Collection<E> c)
    ///                 retainAll(Collection<E> c)

    public String toString() {
        if (size == 0) return "[]";
        String result = "[";
        for (int i = 0; i < size; i++) {
            result += heap[i];
            if (i < size - 1) result += ", ";
        }
        result += "]";
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) heap[i] = null;
        size = 0;
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private int cmp(E a, E b) {
        return ((Comparable<E>) a).compareTo(b);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (cmp(heap[index], heap[parentIndex]) >= 0) break;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void siftDown(int index) {
        int leftChild = 2 * index + 1;
        while (leftChild < size) {
            int smallest = leftChild;
            int rightChild = leftChild + 1;
            if (rightChild < size && cmp(heap[rightChild], heap[leftChild]) < 0)
                smallest = rightChild;
            if (cmp(heap[index], heap[smallest]) <= 0) break;
            swap(index, smallest);
            index = smallest;
            leftChild = 2 * index + 1;
        }
    }

    private void resize() {
        int newCapacity = capacity * 2 + 1;
        E[] newHeap = (E[]) new Comparable[newCapacity];
        for (int i = 0; i < size; i++) newHeap[i] = heap[i];
        this.heap = newHeap;
        this.capacity = newCapacity;
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new NullPointerException("PriorityQueue does not permit null elements");
        if (size == capacity) resize();
        heap[size] = e;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E remove() {
        E result = poll();
        if (result == null) throw new NoSuchElementException();
        return result;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        for (int i = 0; i < size; i++)
            if (o.equals(heap[i])) return true;
        return false;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E poll() {
        if (size == 0) return null;
        E result = heap[0];
        size--;
        if (size > 0) {
            heap[0] = heap[size];
            heap[size] = null;
            siftDown(0);
        } else
            heap[0] = null;
        return result;
    }

    @Override
    public E peek() {
        return size == 0 ? null : heap[0];
    }

    @Override
    public E element() {
        E result = peek();
        if (result == null) throw new NoSuchElementException();
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        for (Object e : c) if (!contains(e)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) throw new NullPointerException();
        boolean modified = false;
        for (E e : c) if (add(e)) modified = true;
        return modified;
    }

    private void removeAt(int i) {
        size--;
        if (i == size)
            heap[i] = null;
        else {
            E moved = heap[size];
            heap[size] = null;
            heap[i] = moved;
            siftDown(i);
            if (heap[i] == moved)
                siftUp(i);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        java.util.ArrayList<E> newList = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++)
            if (!c.contains(heap[i]))
                newList.add(heap[i]);
        boolean modified = (newList.size() != size);
        clear();
        for (E e : newList) {
            heap[size] = e;
            size++;
        }
        if (size > 0)
            for (int i = (size / 2) - 1; i >= 0; i--)
                siftDown(i);
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        java.util.ArrayList<E> newList = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++)
            if (c.contains(heap[i]))
                newList.add(heap[i]);
        boolean modified = (newList.size() != size);
        clear();
        for (E e : newList) {
            heap[size] = e;
            size++;
        }
        if (size > 0)
            for (int i = (size / 2) - 1; i >= 0; i--)
                siftDown(i);
        return modified;
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
    public boolean remove(Object o) {
        return false;
    }
}

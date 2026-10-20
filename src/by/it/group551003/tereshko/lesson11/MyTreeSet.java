package by.it.group551003.tereshko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    ///                 removeAll(Collection)
    ///                 retainAll(Collection)

    private Object[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public MyTreeSet() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private int binarySearch(Object key) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            Comparable<Object> midVal = (Comparable<Object>) array[mid];
            int cmp = midVal.compareTo(key);
            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -(low + 1);
    }

    private void ensureCapacity() {
        if (size >= array.length) {
            Object[] newArray = new Object[array.length * 2];
            for (int i = 0; i < size; i++)
                newArray[i] = array[i];
            array = newArray;
        }
    }

    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            if (i > 0)
                sb.append(", ");
            Object e = array[i];
            sb.append(e == this ? "(this Collection)" : String.valueOf(e));
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
        return binarySearch(o) >= 0;
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
        int pos = binarySearch(e);
        if (pos >= 0)
            return false;
        int insertionPoint = -(pos + 1);
        ensureCapacity();
        for (int i = size; i > insertionPoint; i--)
            array[i] = array[i - 1];
        array[insertionPoint] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int pos = binarySearch(o);
        if (pos < 0)
            return false;
        for (int i = pos; i < size - 1; i++)
            array[i] = array[i + 1];
        array[size - 1] = null;
        size--;
        return true;
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
        int newSize = 0;
        for (int i = 0; i < size; i++)
            if (c.contains(array[i]))
                array[newSize++] = array[i];
            else
                modified = true;
        for (int i = newSize; i < size; i++)
            array[i] = null;
        size = newSize;
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
        for (int i = 0; i < size; i++)
            array[i] = null;
        size = 0;
    }
}

package by.it.group551003.tereshko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ListA() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            if (newCapacity == 0)
                newCapacity = 1;
            Object[] newElements = new Object[newCapacity];
            for (int i = 0; i < size; i++)
                newElements[i] = elements[i];
            elements = newElements;
        }
    }

    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// ///               Обязательные к реализации методы             ///////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        if (size != 0) {
            StringBuilder result = new StringBuilder("[");
            for (int i = 0; i < size; i++) {
                result.append(elements[i]);
                if (i < size - 1)
                    result.append(", ");
            }
            result.append("]");
            return result.toString();
        } else return "[]";
    }

    @Override
    public boolean add(E e) {
        ensureCapacity();
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("");
        else {
            E oldValue = (E) elements[index];
            for (int i = index; i < size - 1; i++)
                elements[i] = elements[i + 1];
            elements[size - 1] = null;
            size--;
            return oldValue;
        }
    }

    @Override
    public int size() {
        return size;
    }

    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// ///               Опциональные к реализации методы             ///////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("");
        ensureCapacity();
        for (int i = size; i > index; i--)
            elements[i] = elements[i - 1];
        elements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("");
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            elements[i] = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elements[i] == null)
                    return i;
        } else
            for (int i = 0; i < size; i++)
                if (o.equals(elements[i]))
                    return i;
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("");
        return (E) elements[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) >= 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
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
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// /////        Эти методы имплементировать необязательно    ////////////
    /// /////        но они будут нужны для корректной отладки    ////////////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}

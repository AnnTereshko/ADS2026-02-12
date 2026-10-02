package by.it.group551003.tereshko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<E> head; // Ссылка на первый элемент
    private Node<E> tail;  // Ссылка на последний элемент
    private int size;      // Текущий размер списка

    public ListB() {
        head = null;
        tail = null;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("");
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
            Node<E> current = head;
            for (int i = 0; i < size; i++) {
                result.append(current.item);
                if (i < size - 1)
                    result.append(", ");
                current = current.next;
            }
            result.append("]");
            return result.toString();
        } else
            return "[]";
    }

    @Override
    public boolean add(E e) {// по умолчанию в конец
        Node<E> newNode = new Node<>(tail, e, null);
        if (head == null)
            head = newNode;
        else
            tail.next = newNode;
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> nodeToRemove = head;
        for (int i = 0; i < index; i++)
            nodeToRemove = nodeToRemove.next;
        E removedItem = nodeToRemove.item;
        Node<E> prevNode = nodeToRemove.prev;
        Node<E> nextNode = nodeToRemove.next;
        if (prevNode == null)
            head = nextNode;
        else {
            prevNode.next = nextNode;
            nodeToRemove.prev = null;
        }
        if (nextNode == null)
            tail = prevNode;
        else {
            nextNode.prev = prevNode;
            nodeToRemove.next = null;
        }
        nodeToRemove.item = null;
        size--;
        return removedItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (index == size) {
            Node<E> l = tail;
            Node<E> newNode = new Node<>(l, element, null);
            tail = newNode;
            if (l == null)
                head = newNode;
            else
                l.next = newNode;
            size++;
        } else {
            Node<E> succ = head;
            for (int i = 0; i < index; i++)
                succ = succ.next;
            Node<E> pred = succ.prev;
            Node<E> newNode = new Node<>(pred, element, succ);
            succ.prev = newNode;
            if (pred == null)
                head = newNode;
            else
                pred.next = newNode;
            size++;
        }
    }

    @Override
    public boolean remove(Object o) {
        Node<E> x = head;
        if (o == null) {
            while (x != null && x.item != null)
                x = x.next;
        } else {
            while (x != null && !o.equals(x.item))
                x = x.next;
        }
        if (x == null)
            return false;
        Node<E> prev = x.prev;
        Node<E> next = x.next;
        if (prev == null)
            head = next;
        else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null)
            tail = prev;
        else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return true;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("");
        Node<E> x = head;
        for (int i = 0; i < index; i++)
            x = x.next;
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        Node<E> current = head;
        while (current != null) {
            Node<E> nextNode = current.next;
            current.item = null;
            current.next = null;
            current.prev = null;
            current = nextNode;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<E> current = head;
        if (o == null) {
            while (current != null) {
                if (current.item == null)
                    return index;
                current = current.next;
                index++;
            }
        } else {
            while (current != null) {
                if (o.equals(current.item))
                    return index;
                current = current.next;
                index++;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("");
        Node<E> x = head;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x.item;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o == null) {
            for (Node<E> x = tail; x != null; x = x.prev) {
                if (x.item == null)
                    return index;
                index--;
            }
        } else {
            for (Node<E> x = tail; x != null; x = x.prev) {
                if (o.equals(x.item))
                    return index;
                index--;
            }
        }
        return -1;
    }


    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// ///               Опциональные к реализации методы             ///////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////


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

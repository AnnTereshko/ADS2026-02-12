package by.it.group551003.tereshko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node<E> head; // Первый элемент
    private Node<E> tail; // Последний элемент (для быстрого добавления в конец)
    private int size;     // Количество элементов

    public ListC() {
        head = null;
        tail = null;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("");
    }

    private Node<E> node(int index) {
        Node<E> curr = head;
        for (int i = 0; i < index; i++)
            curr = curr.next;
        return curr;
    }

    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// ///               Обязательные к реализации методы             ///////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        if (size == 0) return "[]";
        String result = "[";
        Node<E> curr = head;
        for (int i = 0; i < size; i++) {
            result += curr.item;
            if (i < size - 1)
                result += ", ";
            curr = curr.next;
        }
        result += "]";
        return result;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E removedItem;
        if (index == 0) {
            removedItem = head.item;
            head = head.next;
            if (head == null)
                tail = null;
        } else {
            Node<E> prev = node(index - 1);
            Node<E> curr = prev.next;
            removedItem = curr.item;
            prev.next = curr.next;
            if (curr == tail)
                tail = prev;
            curr.next = null;
            curr.item = null;
        }
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
        if (index == 0) {
            Node<E> newNode = new Node<>(element, head);
            head = newNode;
            if (size == 0)
                tail = newNode;
        } else {
            Node<E> prev = node(index - 1);
            Node<E> newNode = new Node<>(element, prev.next);
            prev.next = newNode;
            if (prev == tail)
                tail = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(Object o) {
        if (head == null) return false;
        if (o == null) {
            if (head.item == null) {
                remove(0);
                return true;
            }
            Node<E> curr = head;
            while (curr.next != null) {
                if (curr.next.item == null) {
                    Node<E> removed = curr.next;
                    curr.next = removed.next;
                    if (removed == tail) tail = curr;
                    removed.next = null;
                    removed.item = null;
                    size--;
                    return true;
                }
                curr = curr.next;
            }
        } else {
            if (o.equals(head.item)) {
                remove(0);
                return true;
            }
            Node<E> curr = head;
            while (curr.next != null) {
                if (o.equals(curr.next.item)) {
                    Node<E> removed = curr.next;
                    curr.next = removed.next;
                    if (removed == tail) tail = curr;
                    removed.next = null;
                    removed.item = null;
                    size--;
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Node<E> curr = node(index);
        E oldVal = curr.item;
        curr.item = element;
        return oldVal;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (Node<E> curr = head; curr != null; ) {
            Node<E> next = curr.next;
            curr.item = null;
            curr.next = null;
            curr = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<E> curr = head;
        if (o == null) {
            while (curr != null) {
                if (curr.item == null) return index;
                curr = curr.next;
                index++;
            }
        } else {
            while (curr != null) {
                if (o.equals(curr.item)) return index;
                curr = curr.next;
                index++;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        int index = 0;
        Node<E> curr = head;
        if (o == null) {
            while (curr != null) {
                if (curr.item == null) lastIndex = index;
                curr = curr.next;
                index++;
            }
        } else {
            while (curr != null) {
                if (o.equals(curr.item)) lastIndex = index;
                curr = curr.next;
                index++;
            }
        }
        return lastIndex;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!this.contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            this.add(e);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);
        boolean modified = false;
        int currentIndex = index;
        for (E e : c) {
            this.add(currentIndex, e);
            currentIndex++;
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (int i = size - 1; i >= 0; i--) {
            if (c.contains(this.get(i))) {
                this.remove(i);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(this.get(i))) {
                this.remove(i);
                modified = true;
            }
        }
        return modified;
    }

    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////
    /// ///               Опциональные к реализации методы             ///////
    /// //////////////////////////////////////////////////////////////////////
    /// //////////////////////////////////////////////////////////////////////

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

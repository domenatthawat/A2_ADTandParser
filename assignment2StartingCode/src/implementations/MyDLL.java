package implementations;

import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E> {
    private MyDLLNode head;
    private MyDLLNode tail;
    private int size;

    public MyDLL() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");

        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (index == 0) {
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            MyDLLNode<E> current = getNodeAt(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element.");
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null collection.");

        for (Iterator<? extends E> it = toAdd.iterator(); it.hasNext();) {
            add(it.next());
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNodeAt(index).data;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        MyDLLNode<E> nodeToRemove = getNodeAt(index);

        if (nodeToRemove == head) {
            head = nodeToRemove.next;
            if (head != null) head.prev = null;
        } else if (nodeToRemove == tail) {
            tail = nodeToRemove.prev;
            if (tail != null) tail.next = null;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        if (head == null) tail = null;  // list is empty now
        size--;
        return nodeToRemove.data;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) throw new NullPointerException("Cannot remove null element.");

        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(toRemove)) {
                if (current == head) head = current.next;
                if (current == tail) tail = current.prev;
                if (current.prev != null) current.prev.next = current.next;
                if (current.next != null) current.next.prev = current.prev;

                size--;
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException("Cannot set null element.");

        MyDLLNode<E> nodeToSet = getNodeAt(index);
        E oldData = nodeToSet.data;
        nodeToSet.data = toChange;
        return oldData;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element.");

        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(toFind)) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) throw new NullPointerException("Array cannot be null.");

        if (toHold.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
            toHold = newArray;
        }

        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            toHold[i++] = current.data;
            current = current.next;
        }
        if (toHold.length > size) toHold[size] = null;
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyDLLIterator();
    }

    private MyDLLNode<E> getNodeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");

        MyDLLNode<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    private class MyDLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("No more elements.");
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}
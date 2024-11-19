package implementations;

import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {
    private E[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size >= elements.length) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");

        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element.");

        ensureCapacity();
        elements[size++] = toAdd;
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
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
        return elements[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");

        E removedElement = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) throw new NullPointerException("Cannot remove null element.");

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toRemove)) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) throw new NullPointerException("Cannot set null element.");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");

        E oldElement = elements[index];
        elements[index] = toChange;
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element.");

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) throw new NullPointerException("Array cannot be null.");

        if (toHold.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
            System.arraycopy(elements, 0, newArray, 0, size);
            return newArray;
        }
        System.arraycopy(elements, 0, toHold, 0, size);
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(elements, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<E> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements.");
            }
            return elements[currentIndex++];
        }
    }
}
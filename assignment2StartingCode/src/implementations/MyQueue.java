package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

public class MyQueue<E> implements QueueADT<E> {
    private MyDLL<E> list;

    public MyQueue() {
        list = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the queue.");
        }
        list.add(toAdd);
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty.");
        }
        return list.remove(0);
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty.");
        }
        return list.get(0);
    }

    @Override
    public void dequeueAll() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return list.contains(toFind);
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in the queue.");
        }

        int position = 1;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(toFind)) {
                return position;
            }
            position++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }

        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();

        while (thisIt.hasNext() && thatIt.hasNext()) {
            if (!thisIt.next().equals(thatIt.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return list.toArray(holder);
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        return list.size();
    }
}
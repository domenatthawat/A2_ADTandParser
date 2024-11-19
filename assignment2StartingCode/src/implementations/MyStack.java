package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class MyStack<E> implements StackADT<E> {
    private MyArrayList<E> list; 

    // To create an empty stack.
    public MyStack() {
    	
        list = new MyArrayList<>(); 
    }

    // Add a new element to the stack.
    @Override
    public void push(E toAdd) throws NullPointerException {
    	
        if (toAdd == null) {
        	
            throw new NullPointerException("You cannot add null to the stack.");
        }
        
        list.add(toAdd); 
    }

    // Pop an element off the stack. 
    @Override
    public E pop() throws EmptyStackException {
    	
        if (isEmpty()) {
        	
            throw new EmptyStackException(); 
        }
        
        return list.remove(list.size() - 1); 
    }

    // Peek at the top element.
    @Override
    public E peek() throws EmptyStackException {
    	
        if (isEmpty()) {
        	
            throw new EmptyStackException(); 
        }
        
        return list.get(list.size() - 1); 
    }

    // Clear the stack.
    @Override
    public void clear() {
    	
        list.clear(); 
    }

    // Check if the stack is empty or not.
    @Override
    public boolean isEmpty() {
    	
        return list.size() == 0; 
    }

    // Change the stack to an array.
    @Override
    public Object[] toArray() {
    	
        Object[] array = new Object[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
        	
            array[i] = list.get(list.size() - 1 - i); 
        }
        return array; // Return the array.
    }

    // Change the stack to an array by using the given array .
    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
    	
        if (holder == null) {
        	
            throw new NullPointerException("You cannot pass a null array to toArray.");
        }

        // If the array is too small => make a new array of the correct size.
        if (holder.length < size()) {
        	
            holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), size());
        }

        // Geetbthe elements from the stack into the array.
        int index = 0;
        
        for (int i = size() - 1; i >= 0; i--) {
        	
            holder[index++] = list.get(i); 
        }

        return holder; 
    }

    // Check if the stack contains a specific element.
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
        	
            throw new NullPointerException("Cannot search for empty element in the stack.");
        }
        return list.contains(toFind); 
    }

    // Find an element in the stack and return 1 based index from the top.
    @Override
    public int search(E toFind) {
        if (toFind == null) {
        	
            throw new NullPointerException("You cannot search for null in the stack.");
        }
        
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals(toFind)) {
            	
                return (list.size() - i); 
            }
        }
        return -1; 
    }

    // Loop through the stack elements.
    @Override
    public Iterator<E> iterator() {
    	
        return new Iterator<E>() {
        	
            private int currentIndex = list.size() - 1; 

            @Override
            public boolean hasNext() {
            	
                return currentIndex >= 0; 
            }

            @Override
            public E next() {
            	
                if (!hasNext()) {
                	
                    throw new NoSuchElementException();
                }
                
                return list.get(currentIndex--); 
            }
        };
    }

    // Check if two stacks are equal.
    @Override
    public boolean equals(StackADT<E> that) {
    	
        if (that == null || !(that instanceof MyStack)) {
        	
            return false; 
        }
        if (this.size() != that.size()) return false;
        
        Iterator<E> thisIt = this.iterator();
        
        Iterator<E> thatIt = that.iterator();
        
        while (thisIt.hasNext() && thatIt.hasNext()) {
        	
            if (!thisIt.next().equals(thatIt.next())) {
            	
                return false; 
            }
        }
        return true; 
    }

    // Get the size of the stack.
    @Override
    public int size() {
    	
        return list.size();
    }

    // As this implementation not have a fixed stack size, return false.
    @Override
    public boolean stackOverflow() {
        return false; 
    }
}
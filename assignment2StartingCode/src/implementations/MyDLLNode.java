package implementations;

public class MyDLLNode<E> {
    E data;
    MyDLLNode<E> prev;
    MyDLLNode<E> next;

    public MyDLLNode(E data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
package exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException(String message) {
        super(message);
    }

    public EmptyQueueException() {
        super("Queue is empty.");
    }
}

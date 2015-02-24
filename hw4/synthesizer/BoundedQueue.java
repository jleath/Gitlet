package synthesizer;

public interface BoundedQueue {
    int capacity();
    int fillCount();
    boolean isEmpty();
    boolean isFull();
    void enqueue(double x);
    double dequeue();
    double peek();
}

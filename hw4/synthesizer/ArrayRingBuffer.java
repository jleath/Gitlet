// Make sure to make this class a part of the synthesizer package
package synthesizer;

public class ArrayRingBuffer extends AbstractBoundedQueue {
  /* Index for the next dequeue or peek. */
  private int first;           
  /* Index for the next enqueue. */
  private int last;             
  /* Array for storing the buffer data. */
  private double[] rb;

  /** Create a new ArrayRingBuffer with the given capacity. */
  public ArrayRingBuffer(int capacity) {
    rb = new double[capacity];
    first = 0;
    last = 0;
    this.capacity = capacity;
    fillCount = 0;
  }

  /** Adds x to the end of the ring buffer. If there is no room, then
    * throw new RuntimeException("Ring buffer overflow") 
    */
  public void enqueue(double x) {
    if (isFull()) {
        throw new RuntimeException("Ring buffer overflow");
    } else {
        rb[last] = x;
        last = last + 1;
        if (last == capacity) {
            last = 0;
        }
        fillCount = fillCount + 1;
    }
  }

  /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
    * throw new RuntimeException("Ring buffer underflow");
    */
  public double dequeue() {
    if (isEmpty()) {
        throw new RuntimeException("dequeue on an empty ring buffer");
    }
    double result = rb[first];
    fillCount = fillCount - 1;
    first = first + 1;
    if (first == capacity) {
        first = 0;
    }
    return result;
  }

  /** Return oldest item, but don't remove it. */
  public double peek() {
      if (isEmpty()) {
        throw new RuntimeException("peek on an empty ring buffer");
      }
      return rb[first];
  }

}

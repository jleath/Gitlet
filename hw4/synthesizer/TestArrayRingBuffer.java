package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(12.0);
        assertEquals(12.0, arb.peek(), 1e-10);
        arb.enqueue(13.0);
        assertEquals(12.0, arb.peek(), 1e-10);
        assertEquals(12.0, arb.dequeue(), 1e-10);
        assertEquals(13.0, arb.peek(), 1e-10);
        assertEquals(13.0, arb.dequeue(), 1e-10);
    }

    @Test
    public void testHardcore() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5000);
        for (int i = 0; i < 5000; i++) {
            arb.enqueue(i);
        }
        for (int i = 0; i < 2500; i++) {
            arb.dequeue();
        }
        for (int i = 0; i < 2500; i++) {
            arb.enqueue(i);
        }
        for (int i = 0; i < 5000; i++) {
            arb.dequeue();
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 

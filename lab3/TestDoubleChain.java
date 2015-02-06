import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the DoubleChain class
 */

public class TestDoubleChain {

    /** Tests the constructor of DoubleChain */
    @Test
    public void testConstructor() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5,d.getFront().val, 1e-6);
        assertEquals(null, d.getFront().prev);
        assertEquals(null, d.getFront().next);
    }

    /** Tests the buildChain method */
    @Test
    public void testBuildChain() {
        DoubleChain d = new DoubleChain(1);
        d.insertBack(2);
        d.insertBack(3);
        d.insertBack(4);
        DoubleChain test = DoubleChain.buildChain(1, 2, 3, 4);
        assertTrue(d.toString().equals(test.toString()));
    }

    /** Tests some basic DoubleChain operations. */
    @Test
    public void testBasicOperations() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5, d.getFront().val, 1e-11);
        assertEquals(5, d.getBack().val, 1e-11);

        d.insertFront(2);
        d.insertFront(1);
        d.insertBack(7);
        d.insertBack(8);
        assertEquals(1, d.getFront().val, 1e-11);
        assertEquals(8, d.getBack().val, 1e-11);
    }

    /** Tests the DoubleChain's size method. */
    @Test
    public void testSize() {
        DoubleChain test = DoubleChain.buildChain(1, 2, 3, 4);
        assertEquals(4, test.size());
        test = DoubleChain.buildChain(1);
        assertEquals(1, test.size());
    }

    /** Tests the DoubleChain class' get method. */
    @Test
    public void testGet() {
        DoubleChain test = DoubleChain.buildChain(1, 2, 3, 4);
        assertEquals(4, test.get(3), 1e-11);
    }

    /** Tests the DoubleChain class' deleteByIndex method. */
    @Test
    public void testDeleteByIndex() {
        DoubleChain test = DoubleChain.buildChain(1, 2, 3, 4);
        DoubleChain expected = DoubleChain.buildChain(1, 2, 4);
        test.deleteByIndex(2);
        assertTrue(test.toString().equals(expected.toString())); 
    }

    /** Tests the DoubleChain class' deleteByValue method. */
    @Test
    public void testDeleteByValue() {
        DoubleChain test = DoubleChain.buildChain(1, 3, 2, 3, 3, 3, 4, 3);
        test.deleteByValue(3);
        DoubleChain expected = DoubleChain.buildChain(1, 2, 4);
        assertTrue(expected.toString().equals(test.toString()));
    }

    /** Tests the DoubleChain class' member method. */
    @Test
    public void testIndexOf() {
        DoubleChain test = DoubleChain.buildChain(1, 2, 3, 4);
        assertEquals(-1, test.indexOf(6));
        assertEquals(2, test.indexOf(3));
        assertEquals(0, test.indexOf(1));
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestDoubleChain.class);
    }
}

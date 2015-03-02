import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest {
    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");
        um.put("Test", "Ing");
        assertEquals(um.size(), 2);
        um.clear();
        assertEquals(um.size(), 0);
        um.put("Test", "Who knows?");
        assertEquals(um.get("Test"), "Who knows?");
        um.put("Test", "YUP");
        assertEquals(um.get("Test"), "YUP");
        assertEquals(um.remove("test"), null);
        assertEquals(um.remove("Test"), "YUP");
        assertEquals(um.size(), 0);
        um.put("Test", "MAYBE");
        assertEquals(um.remove("Test", "NOPE"), null);
        assertEquals(um.size(), 1);
        assertEquals(um.get("Test"), "MAYBE");
        assertEquals(um.remove("Test", "MAYBE"), "MAYBE");
        assertEquals(um.size(), 0);
        um.clear();
        assertEquals(um.size(), 0);
        um.put("butt", "hole");
        um.put("big", "dummy");
        um.put("Test", "ing");
        assertEquals(um.size(), 3);
        assertEquals(um.get("butt"), "hole");
        assertEquals(um.get("big"), "dummy");
        assertEquals(um.get("Test"), "ing");
    }

    @Test
    public void testInvert() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        ULLMap<String, Integer> invertedUm = ULLMap.invert(um);
        assertEquals(invertedUm.size(), 3);
        assertEquals((int)invertedUm.get("zero"), 0);
        assertEquals((int)invertedUm.get("one"), 1);
        assertEquals((int)invertedUm.get("two"), 2);
    }

    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        assertEquals(um.size(), 3);
        assertEquals(um.get(1), "one");
        Iterator<Integer> umi = um.iterator();
        assertTrue(umi.hasNext());
        assertEquals((int)umi.next(), 2);
        assertTrue(umi.hasNext());
        assertEquals((int)umi.next(), 1);
        assertTrue(umi.hasNext());
        assertEquals((int)umi.next(), 0);
        assertFalse(umi.hasNext());
    }
    

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
} 

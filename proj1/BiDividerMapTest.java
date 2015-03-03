import org.junit.Test;
import static org.junit.Assert.*;
import ngordnet.BiDividerMap;

/** Tests the SynsetParser class.
 * @author Joshua Leath
 */
public class BiDividerMapTest {

    @Test
    public void testBasic() {
        BiDividerMap<Integer, String> b = new BiDividerMap<Integer, String>();
        b.put(0, "zero");
        b.put(1, "one");
        b.put(2, "two");
        assertEquals(b.getByKey(0), "zero");
        assertEquals(b.getByKey(1), "one");
        assertEquals(b.getByKey(2), "two");
        assertEquals((int)b.getByValue("zero"), 0);
        assertEquals((int)b.getByValue("one"), 1);
        assertEquals((int)b.getByValue("two"), 2);
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BiDividerMapTest.class);
    }
} 

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/** ArrayList61BTest. You should write additional tests.
 *  @author Josh Hug
 */

public class MaxArrayList61BTest {
    @Test
    public void basicTest() {
        List<Integer> L = new ArrayList61B<Integer>();
        L.add(5);
        L.add(10);
        L.add(2);
        L.add(15);
        L.add(13);
        assertTrue(L.contains(5));        
        assertFalse(L.contains(0));
        assertFalse(L.contains(2));
        assertFalse(L.contains(13));
        assertTrue(L.contains(10));

    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ArrayList61BTest.class);
    }
}   

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import ngordnet.SynsetLookupTable;

/** Tests the SynsetParser class.
 * @author Joshua Leath
 */
public class SynsetLookupTableTest {

    @Test
    public void testBasic() {
        SynsetLookupTable test = new SynsetLookupTable();
        test.add("zero", 0);
        test.add("one", 1);
        test.add("two", 2);
        test.add("one", 11);
        Set<String> nouns = test.getNouns();
        for (String s : nouns) {
            System.out.println(s);
        }
        HashSet<Integer> ids = test.getIds("zero");
        for (int i : ids) {
            System.out.print(i);
        }
        System.out.println();
        ids = test.getIds("one");
        for (int i : ids) {
            System.out.print(i);
        }
        System.out.println();
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(SynsetLookupTableTest.class);
    }
} 

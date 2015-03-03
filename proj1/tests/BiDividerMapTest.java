import org.junit.Test;
import static org.junit.Assert.*;
import ngordnet.BiDividerMap;
import ngordnet.Synset;
import ngordnet.SynsetParser;

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

    @Test
    public void testHardcore() {
        BiDividerMap<Integer, Synset> test = new BiDividerMap<Integer, Synset>();
        String line1 = "0,action,dummy";
        String line2 = "1,change,dummy";
        String line3 = "2,demotion,dummy";
        SynsetParser sp = new SynsetParser(line1);
        Synset s = sp.buildSynset();
        test.put(s.getId(), s);
        assertEquals(test.getByKey(0), s);
        sp = new SynsetParser(line2);
        s = sp.buildSynset();
        test.put(s.getId(), s);
        assertEquals(test.getByKey(1), s);
        sp = new SynsetParser(line3);
        s = sp.buildSynset();
        test.put(s.getId(), s);
        assertEquals(test.getByKey(2), s);
        assertEquals((int)test.size(), 3);
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BiDividerMapTest.class);
    }
} 

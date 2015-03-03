import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import ngordnet.Synset;

/** Tests the Synset class.
 * @author Joshua Leath
 */
public class SynsetTest {

    @Test
    public void testBasic() {
        ArrayList<String> synonyms = new ArrayList<String>();
        synonyms.add("This");
        synonyms.add("is");
        synonyms.add("a");
        synonyms.add("test");
        Synset s = new Synset(12, synonyms);
        assertEquals(12, s.getId());
        assertEquals(synonyms, s.getSynonyms());
        assertEquals(4, s.getNumSynonyms());
        /* This should print:
         * This
         * is
         * a
         * test */
        for (String noun : s) {
            System.out.println(noun);
        }
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(SynsetTest.class);
    }
} 

import org.junit.Test;
import static org.junit.Assert.*;
import ngordnet.SynsetParser;
import ngordnet.Synset;

/** Tests the SynsetParser class.
 * @author Joshua Leath
 */
public class SynsetParserTest {

    @Test
    public void testBasic() {
        String line = "0,action,dummy";
        SynsetParser sp = new SynsetParser(line);
        Synset s = sp.buildSynset();
        assertTrue(s.getId() == 0);
        for (String word : s) {
            System.out.println(word);
        }
        line = "4,jump parachuting actified,dummy";
        sp = new SynsetParser(line);
        s = sp.buildSynset();
        assertTrue(s.getId() == 4);
        for (String word : s) {
            System.out.println(word);
        }
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(SynsetParserTest.class);
    }
} 

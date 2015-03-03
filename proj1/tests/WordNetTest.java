import org.junit.Test;
import static org.junit.Assert.*;
import ngordnet.WordNet;

/** Tests the WordNet class.
 * @author Joshua Leath
 */
public class WordNetTest {

    @Test
    public void testBasic() {
        WordNet wn = new WordNet("./wordnet/synsets11.txt", "./wordnet/hyponyms11.txt");
        assertTrue(wn.isNoun("jump"));
        assertTrue(wn.isNoun("leap"));
        assertTrue(wn.isNoun("nasal_decongestant"));

        for (String noun : wn.nouns()) {
            System.out.println(noun);
        }
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(WordNetTest.class);
    }
} 

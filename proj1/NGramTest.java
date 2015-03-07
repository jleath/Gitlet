/** Provides examples of using the NGramMap class.
 *  @author Josh Hug
 */
import ngordnet.NGramMap;

public class NGramTest {
    public static void main(String[] args) {
        NGramMap ngm = new NGramMap("./ngrams/all_words.csv", 
                                    "./ngrams/total_counts.csv");
    }
}


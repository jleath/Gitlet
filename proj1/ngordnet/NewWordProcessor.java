package ngordnet;
import java.util.HashSet;
import java.util.Collection;

/** A class that implements the YearlyRecordProcessor interface, this class provides a process
 *  method that tracks the change in the number of new words found each year.
 *  @author Joshua Leath
 */

public class NewWordProcessor implements YearlyRecordProcessor {

    /** The words that we have seen so far. */
    private HashSet<String> words;

    /** The number of words that we have seen so far. */
    private long numberOfWords;

    /** A flag to mark whether we are processing our first year of data or not,
     *  without doing this, the resulting chart shows a huge spike in the first year
     *  which greatly skews the scope of the chart and makes it difficult to see
     *  any interesting information. */
    private boolean firstYear;

    public NewWordProcessor() {
        words = new HashSet<String>(); 
        numberOfWords = 0;
        firstYear = true;
    }

    public NewWordProcessor(Collection<String> existingWords) {
        this();
        for (String word : existingWords) {
            words.add(word.intern()); 
        }
    }

    public double process(YearlyRecord yr) {
        long initialSize = words.size();
        for (String s : yr.words()) {
            words.add(s.intern());
        }
        return words.size() - initialSize;
    }

    /** Methods for getting relevant information for xChart. */

    public String title() {
        return "New Word Frequency";
    }

    public String xlabel() {
        return "Year";
    }

    public String ylabel() {
        return "New Words";
    }

    public String legend() {
        return "Frequency";
    }
}

package ngordnet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Comparator;

/** An object that stores all word counts for a given year.
 *  @author Joshua Leath
 */

public class YearlyRecord {

    /** The number of words that have been recorded this year. */
    private int wordCount;
    /** A TreeMap that maps from the word's counts to the word. */
    private TreeMap<Number, String> countBased;
    /** A HashMap that maps from the word to the word's counts. */
    private HashMap<String, Integer> wordBased;
    
    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        wordCount = 0;
        countBased = new TreeMap<Number, String>(new Comparator<Number>()
                     {
                         public int compare(Number o1, Number o2)
                         {
                             Integer x = o1.intValue();
                             Integer y = o2.intValue();
                             return x.compareTo(y);
                         }
                     });
        wordBased = new HashMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this();
        wordCount = 0;
        for (String s : otherCountMap.keySet()) {
            put(s, otherCountMap.get(s));
            wordCount = wordCount + 1;
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return getByWord(word);
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return countBased.keySet();
    }

    /** Records that WORD occured COUNT times in this year. */
    public void put(String word, int count) {
        countBased.put(count, word);
        wordBased.put(word, count);
        wordCount = wordCount + 1;
    }

    /** Returns rank of WORD. */
    public int rank(String word) {
        return countBased.tailMap(wordBased.get(word), true).size();
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return wordCount;
    }
    
    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return countBased.values();
    }

    /** Returns the count associated with WORD. */
    private int getByWord(String word) {
        return wordBased.get(word);
    }

    /** Returns the word associated with COUNT. */
    private String getByCount(int count) {
        return countBased.get(count);
    }
}

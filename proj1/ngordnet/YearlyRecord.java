package ngordnet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Collection;
import java.util.TreeSet;
import java.util.ArrayList;

/** An object that stores all word counts for a given year.
 *  @author Joshua Leath
 */

public class YearlyRecord {

    /** A HashMap mapping counts to words. */
    private HashMap<Number, String> countBased;

    /** An ordered hash map that maps words to counts. */
    private LinkedHashMap<String, Number> wordBased;

    /** A HashMap that ranks words to their rank based on frequency.
     *  Rank 1 is the most frequent and rank N is the least frequent where
     *  N is the number of words. */
    private HashMap<String, Integer> ranking;
    
    /** A list for storing duplicate words found when reading in data.
     *  This is so that we don't overwrite relevant data.  These words
     *  are processed when the data is cached.  See the cache method. */
    private ArrayList<ExtraWord> duplicates;

    /** The number of words found. */
    private int wordCount;

    /** A flag to represent whether the data has been cached or not.
     *  See the cache method. */
    private boolean cached;

    /** A nested class to temporarily store duplicate words and their counts. */
    private class ExtraWord {
        private Number count;
        private String word;

        private ExtraWord(Number c, String w) {
            count = c;
            word = w;
        }
    }

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        cached = false;
        wordCount = 0;
        wordBased = new LinkedHashMap<String, Number>();
        countBased = new HashMap<Number, String>();
        ranking = new HashMap<String, Integer>();
        duplicates = new ArrayList<ExtraWord>();
    }

    /** A utility method to help improve the performance of this class, words are not 
     *  sorted or ranked until this method is called.  This method will be called anytime
     *  there is data that has not been cached and some processing needs to occur. */
    public void cache() {
        TreeSet<Number> sortedCounts = new TreeSet<Number>(countBased.keySet());
        ArrayList<ExtraWord> toRemove = new ArrayList<ExtraWord>();
        for (Number n : sortedCounts) {
            wordBased.put(countBased.get(n).intern(), n);
            for (ExtraWord extra : duplicates) {
                if (n.equals(extra.count)) {
                    wordBased.put(extra.word.intern(), n);
                    toRemove.add(extra);
                }
            }
            for (ExtraWord extra : toRemove) {
                duplicates.remove(extra);
            }
            toRemove.clear();
        }
        int totalSize = wordBased.size();
        int i = totalSize;
        for (String word : wordBased.keySet()) {
            ranking.put(word.intern(), i);
            i = i - 1;
        }
        countBased.clear();
        cached = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this();
        for (String s : otherCountMap.keySet()) {
            put(s.intern(), otherCountMap.get(s));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (!cached) {
            cache();
            cached = true;
        }
        if (wordBased.containsKey(word)) {
            return wordBased.get(word).intValue();
        } else {
            return 0;
        }
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        if (!cached) {
            cache();
            cached = true;
        }
        return wordBased.values();
    }

    /** Records that WORD occured COUNT times in this year. */
    public void put(String word, int count) {
        if (countBased.containsKey(count)) {
            duplicates.add(new ExtraWord(count, word.intern()));
            wordCount = wordCount + 1;
            return;
        }
        wordCount = wordCount + 1;
        cached = false;
        countBased.put(count, word.intern());
    }

    /** Returns rank of WORD. */
    public int rank(String word) {
        if (!cached) {
            cache();
            cached = true;
        }
        return ranking.get(word);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return wordCount;
    }
    
    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (!cached) {
            cache();
            cached = true;
        }
        return wordBased.keySet();
    }
}

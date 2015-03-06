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
    private LinkedHashMap<String, Number> wordBased;
    private HashMap<String, Integer> ranking;
    private ArrayList<ExtraWord> duplicates;
    private int wordCount;
    private boolean cached;
    private int sinceLastCache;

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
        sinceLastCache = 0;
    }

    private void cache() {
        TreeSet<Number> sortedCounts = new TreeSet<Number>(countBased.keySet());
        for (Number n : sortedCounts) {
            wordBased.put(countBased.get(n), n);
            for (ExtraWord extra : duplicates) {
                if (n.equals(extra.count)) {
                    wordBased.put(extra.word, n);
                    duplicates.remove(extra);
                    break;
                }
            }
        }
        int totalSize = wordBased.size();
        int i = totalSize;
        for (String word : wordBased.keySet()) {
            ranking.put(word, i);
            i = i - 1;
        }
        countBased.clear();
        cached = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this();
        for (String s : otherCountMap.keySet()) {
            put(s, otherCountMap.get(s));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (!cached) {
            cache();
            cached = true;
        }
        return wordBased.get(word).intValue();
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
            duplicates.add(new ExtraWord(count, word));
            wordCount = wordCount + 1;
            return;
        }
        if (sinceLastCache > 1000000) {
            cache();
            sinceLastCache = 0;
        }
        if (duplicates.size() > 1000) {
            cache();
        }

        wordCount = wordCount + 1;
        sinceLastCache = sinceLastCache + 1;
        cached = false;
        countBased.put(count, word);
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

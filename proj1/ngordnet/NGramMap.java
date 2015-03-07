package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.introcs.In;

/** An object that provides utility methods for making queries on the 
 * Google NGrams dataset (or a subset thereof); An NGramMap stores pertinent data
 * from a "words file" and a "counts file". It is not a map in the strict sense,
 * but it does provide additional functionality.
 * @author Joshua Leath
 */

public class NGramMap {
    private TimeSeries<Long> ts;
    private HashMap<String, TimeSeries<Integer>> wordCounts;
    private HashMap<Integer, YearlyRecord> yrMap;

    public boolean isInMap(String word) {
        return wordCounts.containsKey(word);
    }
    
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFileName, String countsFileName) {
        // Initialize instance variables and containers
        wordCounts = new HashMap<String, TimeSeries<Integer>>();
        ts = new TimeSeries<Long>();
        yrMap = new HashMap<Integer, YearlyRecord>();
        In wordsFile = new In(wordsFileName);
        In countsFile = new In(countsFileName);

        // Read words file
        while (wordsFile.hasNextLine()) {
            String line = wordsFile.readLine();
            int curr = 0;
            StringBuilder wordBuilder = new StringBuilder();
            while (line.charAt(curr) != '\t') {
                wordBuilder.append(line.charAt(curr));
                curr = curr + 1;
            }
            String word = wordBuilder.toString();
            curr = curr + 1;
            if (!wordCounts.containsKey(word)) {
                wordCounts.put(word, new TimeSeries<Integer>());
            }
            int year = 0;
            while (line.charAt(curr) != '\t') {
                year = (year * 10) + Character.getNumericValue(line.charAt(curr));
                curr = curr + 1;
            }
            curr = curr + 1;
            int count = 0;
            while (line.charAt(curr) != '\t') {
                count = (count * 10) + Character.getNumericValue(line.charAt(curr));
                curr = curr + 1;
            }
            curr = curr + 1;
            wordCounts.get(word).put(year, count);
            if (yrMap.containsKey(year)) {
                YearlyRecord yr = getRecord(year);
                yr.put(word, count);
                yrMap.put(year, yr);
            } else {
                YearlyRecord yr = new YearlyRecord();
                yr.put(word, count);
                yrMap.put(year, yr);
            }
        }
        // Read counts file
        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            int curr = 0;
            int year = 0;
            while (line.charAt(curr) != ',') {
                year = (year * 10) + Character.getNumericValue(line.charAt(curr));
                curr = curr + 1;
            }
            curr = curr + 1;
            long count = 0;
            while (line.charAt(curr) != ',') {
                count = (count * 10) + Character.getNumericValue(line.charAt(curr));
                curr = curr + 1;
            }
            ts.put(year, count);
        }
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        return wordCounts.get(word);
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> result = wordCounts.get(word);
        result = new TimeSeries<Integer>(result, startYear, endYear);
        return result;
    }

    /** Returns the absolute count of WORD in the given YEAR. */
    public int countInYear(String word, int year) {
        return wordCounts.get(word).get(year);
    }

    /** Returns a defensive copy of the YearlyRecord in YEAR. */
    public YearlyRecord getRecord(int year) {
        return yrMap.get(year);
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR
     *  as processed by YRP. 
    public TimeSeries<Double> processedHistory(int startYear, int endYear, YearlyRecordProcessor yrp) {
        //TODO LATER
        return new TimeSeries<Double>();
    }
    */

    /** Provides processed history of all words ever as processed by YRP.
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        //TODO LATER
        return new TimeSeries<Double>();
    }
    */

    /** Returns the summed weight relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String word : words) {
            sum = sum.plus(weightHistory(word));
        }
        return sum;
    }

    /** Provides the summed relative frequency of all WORDS between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String word : words) {
            sum = sum.plus(weightHistory(word, startYear, endYear));
        }
        return sum;
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return ts;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Integer> counts = countHistory(word);
        TimeSeries<Long> totals = totalCountHistory();
        return counts.dividedBy(totals);
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> copy = countHistory(word);
        TimeSeries<Integer> counts;
        if (copy == null) {
            System.out.println("copy is null for word " + word);
            counts = new TimeSeries<Integer>();
        } else {
            counts = new TimeSeries<Integer>(countHistory(word), startYear, endYear);
        }
        TimeSeries<Long> totals = new TimeSeries<Long>(totalCountHistory(), startYear, endYear);
        return counts.dividedBy(totals);
    }
}

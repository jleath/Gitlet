package ngordnet;
import java.util.Collection;

/** An object that provides utility methods for making queries on the 
 * Google NGrams dataset (or a subset thereof); An NGramMap stores pertinent data
 * from a "words file" and a "counts file". It is not a map in the strict sense,
 * but it does provide additional functionality.
 * @author Joshua Leath
 */

public class NGramMap {
    
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFileName, String countsFileName) {
        //TODO NOW
    }

    /** Provides a defensive copr of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        //TODO NOW
        return new TimeSeries<Integer>();
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        //TODO NOW
        return new TimeSeries<Integer>();
    }

    /** Returns the absolute count of WORD in the given YEAR. */
    public int countInYear(String word, int year) {
        //TODO NOW
        return 0;
    }

    /** Returns a defensive copy of the YearlyRecord in YEAR. */
    public YearlyRecord getRecord(int year) {
        //TODO NOW
        return new YearlyRecord();
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
        //TODO NOW
        return new TimeSeries<Double>();
    }

    /** Provides the summed relative frequency of all WORDS between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        //TODO NOW
        return new TimeSeries<Double>();
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        //TODO NOW
        return new TimeSeries<Long>();
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        //TODO NOW
        return new TimeSeries<Double>();
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        //TODO NOW
        return new TimeSeries<Double>();
    }
}

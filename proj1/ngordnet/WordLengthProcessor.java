package ngordnet;

/** This class provides a process method that returns the average
 *  word length for a given year.
 *  @author Joshua Leath
 */

public class WordLengthProcessor implements YearlyRecordProcessor {
    
    /** Returns the average word length for a given YearlyRecord YR. */
    public double process(YearlyRecord yr) {
        long totalWords = 0;
        long charCount = 0; 
        for (String s : yr.words()) {
            totalWords = totalWords + 1;
            charCount = charCount + s.length();
        }
        return charCount / totalWords;
    }

    /** Utility methods for getting relevant data for xChart. */

    public String title() {
        return "Average Word Length";
    }

    public String xlabel() {
        return "Year";
    }

    public String ylabel() {
        return "Word Length";
    }

    public String legend() {
        return "Average";
    }

}

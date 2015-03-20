package ngordnet;

/** An object that takes a YearlyRecord and returns a double obtained by 
 *  the process method.
 *  @author Joshua Leath
 */

public interface YearlyRecordProcessor {
    /** Returns some feature of a YearlyRecord as a double. */
    double process(YearlyRecord yearlyRecord);
    String title();
    String xlabel();
    String ylabel();
    String legend();
}

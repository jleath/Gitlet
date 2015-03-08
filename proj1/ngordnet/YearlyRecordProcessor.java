package ngordnet;

public interface YearlyRecordProcessor {
    /** Returns some feature of a YearlyRecord as a double. */
    double process(YearlyRecord yearlyRecord);
    String title();
    String xlabel();
    String ylabel();
    String legend();
}

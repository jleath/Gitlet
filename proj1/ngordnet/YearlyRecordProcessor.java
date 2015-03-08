package ngordnet;

public interface YearlyRecordProcessor {
    /** Returns some feature of a YearlyRecord as a double. */
    double process(YearlyRecord yearlyRecord);
}

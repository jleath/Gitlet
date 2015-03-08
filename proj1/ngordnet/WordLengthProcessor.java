package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yr) {
        long totalWords = 0;
        long charCount = 0; 
        for (String s : yr.words()) {
            totalWords = totalWords + 1;
            charCount = charCount + s.length();
        }
        return charCount / totalWords;
    }

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

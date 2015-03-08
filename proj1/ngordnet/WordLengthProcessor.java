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
}

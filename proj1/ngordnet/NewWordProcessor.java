package ngordnet;
import java.util.HashSet;
import java.util.Collection;

public class NewWordProcessor implements YearlyRecordProcessor {
    private HashSet<String> words;
    private long numberOfWords;
    private boolean firstYear;

    public NewWordProcessor() {
        words = new HashSet<String>(); 
        numberOfWords = 0;
        firstYear = true;
    }

    public NewWordProcessor(Collection<String> existingWords) {
        this();
        for (String word : existingWords) {
            words.add(word); 
        }
    }

    public double process(YearlyRecord yr) {
        long initialSize = numberOfWords;
        numberOfWords = yr.words().size();
        if (firstYear == true) {
            firstYear = false;
            return 0;
        }
        return numberOfWords - initialSize;
    }

    public String title() {
        return "New Word Frequency";
    }

    public String xlabel() {
        return "Year";
    }

    public String ylabel() {
        return "New Words";
    }

    public String legend() {
        return "Frequency";
    }
}

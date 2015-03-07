import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import ngordnet.TimeSeries;

public class InTester {
    public static void main(String[] args) {
        int totalWords = 0;
        HashMap<String, TimeSeries<Integer>> wordCounts = new HashMap<String, TimeSeries<Integer>>();
        String wordsFile = "./ngrams/all_words.csv";
        String countsFile = "./ngrams/total_counts.csv";
        In wordFile = new In(wordsFile);
        In countFile = new In(countsFile);

        while (wordFile.hasNextLine()) {
            String line = wordFile.readLine();
            int curr = 0;
            StringBuilder wordBuilder = new StringBuilder();
            while (line.charAt(curr) != '\t') {
                wordBuilder.append(line.charAt(curr));
                curr = curr + 1;
            }
            String word = wordBuilder.toString();
            if (!wordCounts.containsKey(word)) {
                wordCounts.put(word, new TimeSeries<Integer>());
            }
            totalWords = totalWords + 1;
            curr = curr + 1;
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
        }
        System.out.println("Words read: " + totalWords);
    }
}

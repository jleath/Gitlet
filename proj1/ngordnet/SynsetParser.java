package ngordnet;
import java.util.ArrayList;

public class SynsetParser {
    private int currChar;
    private SynsetLookupTable table;

    public SynsetParser() {
        table = new SynsetLookupTable();
        currChar = 0;
    }

    public Synset buildSynset(String line) {
        int id = getSynsetId(line);
        ArrayList<String> synonyms = getWords(line);
        for (String s : synonyms) {
            table.add(s, id);
        }
        currChar = 0;
        return new Synset(id, synonyms);
    }

    public SynsetLookupTable getLookupTable() {
        return table;
    }
    
    private int getSynsetId(String line) {
        int id = 0;
        while (line.charAt(currChar) != ',') {
            id = (id * 10) + Character.getNumericValue(line.charAt(currChar));
            currChar = currChar + 1;
        }
        currChar = currChar + 1;
        return id;
    }

    private ArrayList<String> getWords(String line) {
        StringBuilder wordBuilder = new StringBuilder();
        ArrayList<String> words = new ArrayList<String>();
        while (line.charAt(currChar) != ',') {
            if (line.charAt(currChar) == ' ') {
                String word = wordBuilder.toString();
                words.add(word);
                wordBuilder = new StringBuilder();
            } else {
                wordBuilder.append(line.charAt(currChar));
            }
            currChar = currChar + 1;
        }
        String word = wordBuilder.toString();
        words.add(word);
        return words;
    }
}

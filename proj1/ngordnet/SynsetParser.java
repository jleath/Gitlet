package ngordnet;
import java.util.ArrayList;

public class SynsetParser {
    private String line;
    private int currChar;

    public SynsetParser(String line) {
        this.line = line;
        currChar = 0;
    }

    public Synset buildSynset() {
        int id = getSynsetId();
        ArrayList<String> synonyms = getWords();
        return new Synset(id, synonyms);
    }
    
    private int getSynsetId() {
        int id = 0;
        while (line.charAt(currChar) != ',') {
            id = (id * 10) + Character.getNumericValue(line.charAt(currChar));
            currChar = currChar + 1;
        }
        currChar = currChar + 1;
        return id;
    }

    private ArrayList<String> getWords() {
        String word = "";
        ArrayList<String> words = new ArrayList<String>();
        while (line.charAt(currChar) != ',') {
            if (line.charAt(currChar) == ' ') {
                words.add(word);
                word = "";
            } else {
                word = word + line.charAt(currChar);
            }
            currChar = currChar + 1;
        }
        words.add(word);
        word = "";
        return words;
    }
}

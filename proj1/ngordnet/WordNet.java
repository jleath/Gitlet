package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.ArrayList;


/** An object that stores the WordNet graph in a manner useful for extracting
 *  all hyponyms of a word.
 *  @author Joshua Leath
 */
public class WordNet {

    /** A hashmap that maps Synset ids to ArrayLists of nouns. */
    private HashMap<Integer, ArrayList<String>> synsets;

    /** A Digraph that represents hyponyms. */
    private Digraph hyponyms;

    /** Splits the string on the remaining line of IN into its individual words 
     * (seperated by whitespace) stores them in an ArrayList. */
    private ArrayList<String> splitWords(In in) {
        String line = In.readString();
        String word = "";
        ArrayList<String> words = new ArrayList<String>();
        char curr = ' ';
        while (true) {
            curr = In.readChar();
            if (curr == ',' || curr == '\n') {
                return words;
            } else if (curr == ' ') {
                ArrayList.add(word);
                word = "";
            } else {
                word = word + curr;
            }
        }
        return words;
    }

    /** Creates a HashMap representing the synsets in a WordNet structure. */
    private void buildSynsets(In in) {
        while (!in.isEmpty()) {
            int synsetID = in.readInt();
            in.readChar();
            ArrayList<String> words = splitWords(in);
            in.readLine();
            synsets.put(synsetID, words);
        }
    }

    /** Creates a Digraph representing the hyponyms in a WordNet structure. */
    private void buildHyponyms(In in) {
        char curr == ' ';
        int v, w;
        while(!in.isEmpty()) {
            v = in.readInt();
            curr = in.readChar();
            while (curr != '\n') {
                w = in.readInt();
                hyponyms.addEdge(v, w);
                curr = in.readChar();
            }
        }
    }

    /** Creates a WordNet using files from SYNSETFILENAME and HYPONYMFILENAME. */
    public WordNet(String synsetFilename, String hyponymFilename) {
        In synsetFile = new In(synsetFilename);
        In hyponymFile = new In(hyponymFilename);
        // Store all the synsets
        buildSynsets(synsetFile);
        hyponyms = new Digraph(synsets.size());
        buildHyponyms(hyponymFile);
    }

    /** Returns the set of all nouns in this WordNet.
     */
    public Set<String> nouns() {
        //TODO
    }

    /** Returns true if NOUN is indeed a noun, else false.
     */
    public boolean isNoun(String noun) {
        //TODO
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
     *  WORD.  If WORD belongs to multiple synsets, return all hyponyms of
     *  all of these synsets. Does not include hyponyms of synonyms.
     */
    public Set<String> hyponyms(String word) {
        //TODO
    }
}

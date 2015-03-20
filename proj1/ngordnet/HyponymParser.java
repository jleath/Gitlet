package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

/** This is a utility class designed to read and process hyponyms found in the WordNet dataset.
 *  
 *  Hyponyms are contained in the wordnet directory and give categorical relationships for nouns.
 *  For example: "husky", "detent", and "hot_dog" are hyponyms for the word "dog".
 *
 *  Currently, this code reads in hyponyms by building them up one character at a time.  At some
 *  point, I would like to change this to use Java's split method to clean things up.
 *  @author Joshua Leath
 */

public class HyponymParser {

    /** A scanner that scans the file that details hyponyms in the following format:
     *  <ID>,<ID>,<ID> where <ID> is the id of a given noun. */
    In in;

    /** A digraph of the hyponyms detailed in the given file. */
    Digraph hyponyms;

    /** The number of vertices the digraph will need to store. */
    int numVertices;

    /** The index of the next character to read in hyponymFile. */
    int currChar;

    public HyponymParser(In in, int numWords) {
        numVertices = numWords;
        currChar = 0;
        this.in = in;
        hyponyms = new Digraph(numVertices);
    }

    /** Returns a directed graph whose vertices represent nouns and edges represent the categorical
     *  relationship between those nouns. */
    public Digraph buildHyponymGraph() {
        while (in.hasNextLine()) {
            String line = in.readLine();
            int baseId = getNextId(line);
            while (currChar < line.length()) {
                hyponyms.addEdge(baseId, getNextId(line));
            }
            currChar = 0;
        }
        return hyponyms;
    }

    /** A utility function that reads individual tokens in the hyponyms file. */
    private int getNextId(String line) {
        int v = 0;
        while (currChar < line.length() && line.charAt(currChar) != ',') {
            v = (v * 10) + Character.getNumericValue(line.charAt(currChar));
            currChar = currChar + 1;
        }
        // Skip past the comma
        currChar = currChar + 1;
        return v;
    }
}

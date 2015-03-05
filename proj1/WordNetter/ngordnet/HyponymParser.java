package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

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

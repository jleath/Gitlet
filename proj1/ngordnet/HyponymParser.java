package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

class HyponymParser {
    Digraph hyponyms;

    public HyponymParser(In in, int digraphSize) {
        hyponyms = new Digraph(digraphSize);
        while (in.hasNextLine()) {
            String line = in.readLine();
            int currChar = 0;
            int v = 0;
            int w = 0;
            while (line.charAt(currChar) != ',') {
                // get first Id of hyponym
                v = (v * 10) + Character.getNumericValue(line.charAt(currChar));
                currChar = currChar + 1;
            }
            // skip past the comma
            currChar = currChar + 1;
            while (currChar < line.length() && line.charAt(currChar) != '\n') {
                // get the rest of the nouns and add them to the digraph
                while (currChar < line.length() && line.charAt(currChar) != ',') {
                    w = (w * 10) + Character.getNumericValue(line.charAt(currChar));
                    currChar = currChar + 1;
                }
                hyponyms.addEdge(v, w);
                currChar = currChar + 1;
                w = 0;
            }
        }
    }
}

package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/** An object that stores the WordNet graph in a manner useful for extracting
 *  all hyponyms of a word.
 *  @author Joshua Leath
 */
public class WordNet {

    /** A hashmap that maps Synset ids to ArrayLists of nouns. */
    private HashMap<Integer, Synset> synsets;
    /** A Digraph that represents hyponyms. */
    private Digraph hypo;
    /** A mapping from nouns to ArrayLists of the synset ids that they are
     *  members of. */
    private SynsetLookupTable lookupTable;

    /** Creates a BiDividerMap representing the synsets in a WordNet structure. */
    private void buildSynsets(In in) {
        SynsetParser sp = new SynsetParser();
        Synset synonyms;
        while (in.hasNextLine()) {
            synonyms = sp.buildSynset(in.readLine());
            synsets.put(synonyms.getId(), synonyms);
        }
        lookupTable = sp.getLookupTable();
    }

    /** Creates a Digraph representing the hyponyms in a WordNet structure. */
    private void buildHyponyms(In in) {
        HyponymParser hp = new HyponymParser(in, synsets.size());
        hypo = hp.buildHyponymGraph();
    }

    /** Creates a WordNet using files from SYNSETFILENAME and HYPONYMFILENAME. */
    public WordNet(String synsetFilename, String hyponymFilename) {
        synsets = new HashMap<Integer, Synset>();
        // Files to read in data from.
        In synsetFile = new In(synsetFilename);
        In hyponymFile = new In(hyponymFilename);
        // Store all the synsets
        buildSynsets(synsetFile);
        // Build a directed graph of synset ids.
        buildHyponyms(hyponymFile);
    }

    /** Returns the set of all nouns in this WordNet.
     */
    public Set<String> nouns() {
        return lookupTable.getNouns();
    }

    /** Returns true if NOUN is indeed a noun, else false.
     */
    public boolean isNoun(String noun) {
        return lookupTable.getNouns().contains(noun);
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
     *  WORD.  If WORD belongs to multiple synsets, return all hyponyms of
     *  all of these synsets. Does not include hyponyms of synonyms.
     */
    public Set<String> hyponyms(String word) {
        if (!isNoun(word)) {
            throw new IllegalArgumentException(word + " is not a noun in the system.");
        }
        Set<Integer> ids = lookupTable.getIds(word);
        Set<Integer> desc = GraphHelper.descendants(hypo, ids);
        HashSet<String> result = new HashSet<String>();
        for (int i : ids) {
            Synset s = synsets.get(i);
            for (String noun : s) {
                result.add(noun);
            }
        }
        for (int i : desc) {
            Synset s = synsets.get(i);
            for (String noun : s) {
                result.add(noun);
            }
        }
        result.add(word);
        return result;
    }
}

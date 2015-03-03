package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

/** An object that stores the WordNet graph in a manner useful for extracting
 *  all hyponyms of a word.
 *  @author Joshua Leath
 */
public class WordNet {

    /** A hashmap that maps Synset ids to ArrayLists of nouns. */
    private BiDividerMap<Integer, Synset> synsets;
    /** A Digraph that represents hyponyms. */
    private Digraph hyponyms;

    /** Creates a BiDividerMap representing the synsets in a WordNet structure. */
    private void buildSynsets(In in) {
        SynsetParser sp;
        Synset synonyms;
        while (in.hasNextLine()) {
            sp = new SynsetParser(in.readLine());
            synonyms = sp.buildSynset();
            synsets.put(synonyms.getId(), synonyms);
        }
    }

    /** Creates a Digraph representing the hyponyms in a WordNet structure. */
    private void buildHyponyms(In in) {
        HyponymParser hp = new HyponymParser(in, synsets.size());
        hyponyms = hp.hyponyms;
    }

    /** Creates a WordNet using files from SYNSETFILENAME and HYPONYMFILENAME. */
    public WordNet(String synsetFilename, String hyponymFilename) {
        synsets = new BiDividerMap<Integer, Synset>();
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
        HashSet<String> words = new HashSet<String>();
        for (Synset s : synsets.getValues()) {
            for (String word : s) {
                words.add(word);
            }
        }
        return words;
    }

    /** Returns true if NOUN is indeed a noun, else false.
     */
    public boolean isNoun(String noun) {
        for (Synset s : synsets.getValues()) {
            if (s.contains(noun)) {
                return true;
            }
        }
        return false;
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
     *  WORD.  If WORD belongs to multiple synsets, return all hyponyms of
     *  all of these synsets. Does not include hyponyms of synonyms.
     */
    public Set<String> hyponyms(String word) {
        //TODO
        return new HashSet<String>();
    }
}

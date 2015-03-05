package ngordnet;
import java.util.ArrayList;
import java.util.Iterator;

/** A data structure representing a synonym set, a Synset has an integer
 *  for the ID number of the Synset in the WordNet structure, and a List of 
 *  the synonyms in the set.
 */

public class Synset implements Iterable<String>{
    /** The Id of the Synset. */
    private int id;

    /** The set of synonyms in the Synset. */
    private ArrayList<String> synonymSet;

    /** Constructor. */
    public Synset(int idNum, ArrayList<String> synSet) {
        id = idNum;
        synonymSet = synSet;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getSynonyms() {
        return synonymSet;
    }

    public int getNumSynonyms() {
        return synonymSet.size();
    }

    public Iterator<String> iterator() {
        return synonymSet.iterator();
    }

    public boolean contains(String s) {
        return synonymSet.contains(s);
    }

    public boolean equals(Synset other) {
        return getId() == other.getId() && getSynonyms().equals(other.getSynonyms());
    }
}

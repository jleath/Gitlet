package ngordnet;
import java.util.ArrayList;
import java.util.Iterator;

/** A data structure representing a synonym set, a Synset has an integer
 *  for the ID number of the Synset in the WordNet structure, and a List of 
 *  the synonyms in the set.
 *  @author Joshua Leath
 */

public class Synset implements Iterable<String> {
    /** The Id of the Synset. */
    private int id;

    /** The set of synonyms in the Synset. */
    private ArrayList<String> synonymSet;

    /** Constructor. */
    public Synset(int idNum, ArrayList<String> synSet) {
        id = idNum;
        synonymSet = synSet;
    }

    /** Returns the numerical id of this Synset. */
    public int getId() {
        return id;
    }

    /** Returns an ArrayList of the synonyms found in this synset. */
    public ArrayList<String> getSynonyms() {
        return synonymSet;
    }

    /** Returns the number of synonyms in this synset. */
    public int getNumSynonyms() {
        return synonymSet.size();
    }

    /** An iterator for this Synset. */
    public Iterator<String> iterator() {
        return synonymSet.iterator();
    }

    /** Returns true if this Synset contains the String S, else false. */
    public boolean contains(String s) {
        return synonymSet.contains(s);
    }

    /** Returns true if this Synset is equal to the Synset OTHER, else false. */
    public boolean equals(Synset other) {
        return getId() == other.getId() && getSynonyms().equals(other.getSynonyms());
    }
}

package ngordnet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.introcs.In;

/** A class that stores a two-way mapping structure to store synonym sets,
 *  as well as a lookup table to associate each noun in a WordNet structure
 *  with their synset ID numbers.
 *  @author Joshua Leath
 */

public class SynsetLookupTable {

    /** A HashMap that will serve as a lookup table to associate individual
     *  nouns to their respective synset id numbers. */
    private HashMap<String, HashSet<Integer>> lookupTable;
    
    public SynsetLookupTable() {
        lookupTable = new HashMap<String, HashSet<Integer>>();
    }

    /** Add a mapping from NOUN to ID.
     */
    public void add(String noun, int id) {
        if (!lookupTable.containsKey(noun)) {
            HashSet<Integer> ids = new HashSet<Integer>();
            ids.add(id);
            lookupTable.put(noun, ids); 
        } else {
            HashSet<Integer> ids = lookupTable.get(noun);
            ids.add(id);
            lookupTable.put(noun, ids);
        }
    }

    /** Returns an ArrayList that contains the ids of all the synsets
     *  that NOUN is a member of.
     */
    public HashSet<Integer> getIds(String noun) {
        return lookupTable.get(noun);
    }

    /** Returns a Set of all the words in the lookup table.
     */
    public Set<String> getNouns() {
        return lookupTable.keySet(); 
    }
}

import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator;
import java.util.NoSuchElementException;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K, V> implements Map61B<K, V>, Iterable<K> { //FIX ME
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;

    /** Returns an iterator for ULLMaps.
     */
    public Iterator<K> iterator() {
        return new ULLMapIter();        
    }

    /** ULLMap constructor.
     */
    public ULLMap() {
        size = 0;
        front = null;
    }
    
    /** A static method that returns a new ULLMap that is inverted,
     *  the keys of the original map are the values of the new map,
     *  and the values of the original map are the keys of the new map.
     */
    public static <K, V> ULLMap<V, K> invert(ULLMap<K, V> map) {
        ULLMap<V, K> newMap = new ULLMap<V, K>();
        for (K item : map) {
            newMap.put(map.get(item), item);
        }
        return newMap;
    }

    @Override
    public V get(K key) {
        if (front == null) {
            return null;
        }
        return front.get(key).val;
    }

    @Override
    public void put(K key, V val) {
        if (size == 0) {
            front = new Entry(key, val, null);
            size = size + 1;
        } else if (containsKey(key)) {
            front.get(key).val = val;          
        } else {
            front = new Entry(key, val, front);
            size = size + 1;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (front == null) {
            return false;
        }
        return front.get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = null; 
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
    
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(K k) {
            Entry curr = this;
            if (curr == null) {
                return null;
            }
            while (curr != null) {
                if (curr.key.equals(k)) {
                    return curr;
                }
                curr = curr.next;
            }
            return null;
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private K key;
        /** Stores the value of the key-value pair of this node in the list. */
        private V val;
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public V remove(K key) {
        if (front == null) {
            return null;
        }
        if (front.key.equals(key)) {
            V result = front.val;
            front = front.next;
            size = size - 1;
            return result; 
        }
        Entry curr = front.next;
        Entry prev = front;
        while (curr != null) {
            if (curr.key.equals(key)) {
                V result = curr.val;
                prev.next = curr.next;
                size = size - 1;
                return result;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        V existing = remove(key);
        if (existing == null) {
            return null;
        }
        else if (existing.equals(value)) {
            return existing;
        } else {
            put(key, existing);
            return null;
        }
    }

    @Override
    public Set<K> keySet() { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }
   
    private class ULLMapIter implements Iterator<K> {
        private Entry curr;

        public ULLMapIter() {
            curr = front; 
        }

        public boolean hasNext() {
            return curr != null;
        }

        public K next() {
            if (curr == null) {
                throw new NoSuchElementException();
            }
            K result = curr.key;
            curr = curr.next;
            return result;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }


    }

}

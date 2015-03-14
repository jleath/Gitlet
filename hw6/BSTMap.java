import java.util.Set;
/** This BST will always have terminating nodes with null keys. */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private K key;
    private V value;
    private BSTMap<K, V> left;
    private BSTMap<K, V> right;

    private BSTMap(K key, V val, BSTMap<K, V> l, BSTMap<K, V> r) {
        this.key = key;
        value = val;
        left = l;
        right = r;
    }

    public BSTMap(K key, V val) {
        this(key, val, null, null);
    }

    public BSTMap() {
        this(null, null, null, null);
    }
    
    /** Removes all of the mappings from this map. */
    public void clear() {
        key = null;
        value = null;
        left = null;
        right = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (this.key == null) {
            return false;
        } else if (this.key.compareTo(key) == 0) {
            return true;
        } else {
            return left.containsKey(key) || right.containsKey(key);
        }
    }

    /** Returns the value to which the specified key is mapped,
     *  or null if the map contains no mapping for the key. */
    public V get(K key) {
        if (this.key.compareTo(key) == 0) {
            return value;
        } else if (this.key.compareTo(key) > 0 && left != null) {
            return left.get(key);
        } else if (this.key.compareTo(key) < 0 && right != null) {
            return right.get(key);
        } else {
            return null;
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        if (this.key == null) {
            return 0;
        } else {
            return 1 + left.size() + right.size();
        }
    }

    /** Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (this.key == null) {
            this.key = key;
            this.value = value;
            this.left = new BSTMap<K, V>();
            this.right = new BSTMap<K, V>();
        } else if (this.key.compareTo(key) == 0) {
            return;
        } else if (this.key.compareTo(key) < 0) {
            right.put(key, value);
        } else {
            left.put(key, value);
        }
    }

    public void printInOrder() {
        if (this.key == null) {
            return;
        } else {
            left.printInOrder();
            System.out.println(key);
            right.printInOrder();
        }
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}

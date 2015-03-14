import java.util.Set;

public class CleanBST<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K label;
        V value;

        private Node(K key, V val) {
            label = key;
            value = val;
        }
    }

    private Node root;
    private CleanBST<K, V> left;
    private CleanBST<K, V> right;

    public CleanBST(K key, V value) {
        root = new Node(key, value);
        left = new CleanBST<K, V>();
        right = new CleanBST<K, V>();
    }

    public CleanBST() {
        root = null;
        left = null;
        right = null;
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /** Returns the value to which the specified key is mapped,
     *  or null if the map contains no mapping for the key. */
    public V get(K key) {
        if (root == null) {
            return null;
        } else if (root.label.compareTo(key) == 0) {
            return root.value;
        } else if (root.label.compareTo(key) < 0) {
            return right.get(key);
        } else {
            return left.get(key);
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return 1 + left.size() + right.size();
        }
    }

    /** Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            left = new CleanBST<K, V>();
            right = new CleanBST<K, V>();
        } else if (root.label.compareTo(key) == 0) {
            return;
        } else if (root.label.compareTo(key) < 0) {
            right.put(key, value);
        } else {
            left.put(key, value);
        }
    }

    public void printInOrder() {
        if (root == null) {
            return;
        } else {
            left.printInOrder();
            System.out.println(root.label);
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

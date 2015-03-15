import java.util.Set;
import java.util.HashSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        K label;
        V value;
        Node left;
        Node right;

        public Node(K k, V v) {
            label = k;
            value = v;
            left = null;
            right = null;
        }
    }

    public BSTMap() {
        root = null;
    }

    public Node max() {
        return max(root);
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Node min() {
        return min(root);
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
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
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int compKey = key.compareTo(root.label);
        if (compKey == 0) {
            return x.value;
        } else if (compKey < 0) {
            return get(x.left, key);
        } else {
            return get(x.right, key);
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return 1 + size(x.left) + size(x.right);
    }

    /** Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value);
        }
        int compKey = key.compareTo(x.label);
        if (compKey < 0) {
            x.left = put(x.left, key, value);
        } else if (compKey > 0) {
            x.right = put(x.right, key, value);
        }
        return x;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        System.out.println(x.label + ": " + x.value);
        printInOrder(x.right);
    }

    public V remove(K key) {
        V result = get(key);
        root = remove(root, key);
        return result;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int compKey = key.compareTo(x.label);
        if (compKey < 0) {
            x.left = remove(x.left, key);
        } else if (compKey > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            } else if (x.left == null) {
                return x.right;
            } else {
                Node toRemove = findSuccessor(x);
                x.label = toRemove.label;
                x.value = toRemove.value;
                x.right = remove(x.right, x.label);
            }
        }
        return x;
    }

    private Node findSuccessor(Node x) {
        return min(x.right);
    }

    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    public Set<K> keySet() {
        HashSet<K> result = new HashSet<K>();
        return keySet(result, root);
    }

    private Set<K> keySet(Set<K> result, Node x) {
        if (x == null) {
            return result;
        }
        result = keySet(result, x.left);
        result.add(x.label);
        result = keySet(result, x.right);
        return result;
    }
}

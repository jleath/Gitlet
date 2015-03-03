package ngordnet;
import java.util.HashMap;
import java.util.Set;

public class BiDividerMap<K, V> {
    private HashMap<K, V> keyBased;
    private HashMap<V, K> valueBased;

    public BiDividerMap() {
        keyBased = new HashMap<K, V>();
        valueBased = new HashMap<V, K>();
    }

    public void put(K key, V value) {
        keyBased.put(key, value);
        valueBased.put(value, key);
    }

    public V getByKey(K key) {
        return keyBased.get(key);
    }

    public K getByValue(V value) {
        return valueBased.get(value);
    }

    public Set<V> getValues() {
        return valueBased.keySet();
    }

    public Set<K> getKeys() {
        return keyBased.keySet();
    }

    public int size() {
        return keyBased.size();
    }
}

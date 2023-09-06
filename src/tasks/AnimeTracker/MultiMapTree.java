/**
 * @file MultiMapTree.java
 * @brief A MultiMapTree class that associates multiple values with a single key.
 *
 * This class provides the ability to associate multiple values with a single key.
 *
 * @tparam K The type of keys.
 * @tparam V The type of values.
 */
package tasks.AnimeTracker;
import java.util.*;

/**
 * @class MultiMapTree
 * @brief This class represents a MultiMapTree that associates multiple values with a single key.
 * @tparam K The type of keys.
 * @tparam V The type of values.
 */
public class MultiMapTree<K, V> {
    private Map<K, LinkedHashSet<V>> map;
    private int size;

    /**
     * @brief Initializes a new MultiMapTree.
     */
    MultiMapTree() {
        map = new HashMap<>();
    }

    /**
     * @brief Inserts a value associated with the specified key.
     *
     * @param key   The key to associate the value with.
     * @param value The value to insert.
     */
    public void insert(K key, V value) {
        if(key != null){
            if(contains(key)){
                map.get(key).add(value);
            }
            else {
                LinkedHashSet<V> temp = new LinkedHashSet<>();
                temp.add(value);
                map.put(key, temp);
            }
            ++size;
        }
    }

    /**
     * @brief Removes the entire key-value mapping for the specified key.
     *
     * @param key The key to remove along with its associated values.
     */
    public void remove(K key) {
        if (key != null && contains(key)) {
            size -= map.get(key).size();
            map.remove(key);
        }
    }

    /**
     * @brief Removes a specific value associated with the specified key.
     *
     * @param key   The key to remove the value from.
     * @param value The value to remove.
     */
    public void remove(K key, V value) {
        if (key != null && contains(key, value)) {
            --size;
            map.get(key).remove(value);
        }
    }

    /**
     * @brief Retrieves a collection of values associated with the specified key.
     *
     * @param key The key to retrieve values for.
     * @return A collection of values associated with the key, or null if the key is not found.
     */
    public Collection<V> get(K key) {
        if (key != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * @brief Checks if the MultiMapTree contains the specified key-value pair.
     *
     * @param key   The key to check.
     * @param value The value to check.
     * @return True if the key-value pair is found; otherwise, false.
     */
    public boolean contains(K key, V value) {
        if (contains(key)) {
            return map.get(key).contains(value);
        }
        return false;
    }

    /**
     * @brief Checks if the MultiMapTree contains the specified key.
     *
     * @param key The key to check.
     * @return True if the key is found; otherwise, false.
     */
    public boolean contains(K key) {
        if (key != null) {
            return map.containsKey(key);
        }
        return false;
    }

    /**
     * @brief Returns the number of key-value pairs in the MultiMapTree.
     *
     * @return The number of key-value pairs.
     */
    public int size() {
        return size;
    }

    /**
     * @brief Checks if the MultiMapTree is empty.
     *
     * @return True if the MultiMapTree is empty; otherwise, false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @brief Retrieves a set of all keys in the MultiMapTree.
     *
     * @return A Collection of keys.
     */
    public Collection<K> keySet() {
        return map.keySet();
    }

    /**
     * @brief Clears all data from the MultiMapTree.
     */
    public void clear() {
        size = 0;
        map.clear();
    }

    /**
     * @brief Returns a string representation of the MultiMapTree.
     *
     * @return A string representation of the MultiMapTree.
     */
    @Override
    public String toString() {
        return map.toString();
    }
}

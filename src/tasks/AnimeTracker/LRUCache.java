/**
 * @file LRUCache.java
 * @brief This file contains the LRUCache class, which is a generic implementation of a Least Recently Used (LRU) cache.
 */

package tasks.AnimeTracker;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @class LRUCache
 * @brief This class represents a Least Recently Used (LRU) cache.
 * @tparam K The type of keys in the cache.
 * @tparam V The type of values in the cache.
 */
public class LRUCache<K, V> {
    private int capacity;
    private Queue<Pair<K, V>> entryQueue;
    private MultiMapTree<K, V> baseStructure;

    /**
     * @brief Constructor for LRUCache class with a specified capacity.
     * @param capacity The maximum capacity of the cache.
     */
    LRUCache(int capacity) {
        this.capacity = capacity <= 0 ? 10 : capacity;
        entryQueue = new LinkedList<>();
        baseStructure = new MultiMapTree<>();
    }

    /**
     * @brief Default constructor for LRUCache class with a default capacity of 10.
     */
    LRUCache() {
        this(10);
    }

    /**
     * @brief Get a collection of values associated with a key.
     * @param key The key to look up in the cache.
     * @return A collection of values associated with the key.
     */
    public Collection<V> get(K key) {
        return baseStructure.get(key);
    }

    /**
     * @brief Add a key-value pair to the cache.
     * @param key The key to add to the cache.
     * @param value The value associated with the key.
     */
    public void add(K key, V value) {
        makeSpace();
        baseStructure.insert(key, value);
        entryQueue.add(new Pair<>(key, value));
    }

    /**
     * @brief Check if the cache is empty.
     * @return True if the cache is empty, false otherwise.
     */
    public boolean isEmpty() {
        return baseStructure.isEmpty();
    }

    /**
     * @brief Get the current size of the cache.
     * @return The size of the cache.
     */
    public int size() {
        return baseStructure.size();
    }

    /**
     * @brief Get the maximum capacity of the cache.
     * @return The maximum capacity of the cache.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * @brief Clear the cache, removing all entries.
     */
    public void clear() {
        baseStructure.clear();
        entryQueue.clear();
    }

    /**
     * @brief Remove the least recently used (LRU) entry from the cache.
     */
    public void removeLRU() {
        if (!isEmpty()) {
            baseStructure.remove(entryQueue.peek().getKey(), entryQueue.peek().getValue());
            entryQueue.poll();
        }
    }

    /**
     * @brief Get the least recently used (LRU) entry from the cache.
     * @return The least recently used (LRU) entry.
     */
    public Pair<K, V> getLRU() {
        return entryQueue.peek();
    }

    private void makeSpace() {
        if (size() == capacity) {
            baseStructure.remove(entryQueue.peek().getKey(), entryQueue.peek().getValue());
            entryQueue.poll();
        }
    }

    /**
     * @brief Get a string representation of the cache.
     * @return A string representation of the cache.
     */
    @Override
    public String toString() {
        return baseStructure.toString();
    }
}

/**
 * @class Pair
 * @brief This class represents a key-value pair.
 * @tparam K The type of the key.
 * @tparam V The type of the value.
 */
class Pair<K, V> {
    private K key;
    private V value;

    /**
     * @brief Constructor for Pair class.
     * @param key The key of the pair.
     * @param value The value of the pair.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @brief Get the key of the pair.
     * @return The key of the pair.
     */
    public K getKey() {
        return key;
    }

    /**
     * @brief Get the value of the pair.
     * @return The value of the pair.
     */
    public V getValue() {
        return value;
    }

    /**
     * @brief Set the key of the pair.
     * @param key The new key for the pair.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * @brief Set the value of the pair.
     * @param value The new value for the pair.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * @brief Get a string representation of the pair.
     * @return A string representation of the pair in the format "(key, value)".
     */
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}

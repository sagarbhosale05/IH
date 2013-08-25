/**
 * Cache.java
 */
package com.ih.imagecache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class that encapsulates the cache.  This cache implementation implements the strong and weak reference cache.
 *
 */
public class Cache<K, V> {

    /**
     * The soft reference cache.
     */
    private Map<K, SoftReference<V>> softReferenceCache;

    /**
     * The hard reference cache.
     */
    private Map<K, V> hardReferenceCache;

    /**
     * The constructor that takes in the initial size of the cache as argument.
     *
     * @param initialCapacity the initial size of the cache.
     */
    public Cache(final int initialCapacity) {
        // cache, with a soft references
        softReferenceCache = new ConcurrentHashMap<K, SoftReference<V>>(initialCapacity / 2);
        hardReferenceCache = new LinkedHashMap<K, V>(initialCapacity / 2, 0.75f, true) {

            /**
             * {@inheritDoc}
             */
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                if (size() > initialCapacity) {
                    // Entries push-out of hard reference cache are transferred to soft reference cache
                    softReferenceCache.put(eldest.getKey(), new SoftReference<V>(eldest.getValue()));
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * Returns the value from the cache.  Null if the key is not found in the cache or if the key was found but
     * the value was null because it was garbage collected.
     *
     * @param key the key whose value we want to find from the cache.
     * @return The value reference from the cache.
     */
    public V get(final K key) {
        synchronized (hardReferenceCache) {
            final V value = hardReferenceCache.get(key);
            if (value != null) {
                hardReferenceCache.remove(key);
                hardReferenceCache.put(key, value);
                return value;
            }
        }

        final SoftReference<V> valueSoftReference = softReferenceCache.get(key);
        if (valueSoftReference == null) return null;

        final V value = valueSoftReference.get();
        if (value != null) return value;

        softReferenceCache.remove(key);
        return null;
    }

    /**
     * Puts the value in the cache.
     *
     * @param key   the key.
     * @param value the value.
     */
    public void put(final K key, final V value) {
        synchronized (softReferenceCache) {
            softReferenceCache.put(key, new SoftReference<V>(value));
        }
    }

    /**
     * Clears the cache.
     */
    public void clear() {
        softReferenceCache.clear();
        hardReferenceCache.clear();
    }
}

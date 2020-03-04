package net.snatchTech.cacheEviction;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCacheThreeMaps<K,V> implements Cache<K,V> {
    
    private final HashMap<K,V> cache;
    private final HashMap<K, Long> frequency;
    private final HashMap<Long, LinkedHashSet<K>> frequencySets;
    private final int capacity;
    private long min = -1L;

    LFUCacheThreeMaps(int capacity) {

        this.capacity = capacity;
        cache = new HashMap<>();
        frequency = new HashMap<>();
        frequencySets = new HashMap<>();
        frequencySets.put(1L, new LinkedHashSet<>());

    }

    public V get(K key) {

        if(!cache.containsKey(key))
            // cache miss
            return null;

        long count = frequency.get(key);
        long newCount = count + 1L;
        frequency.put(key, newCount);
        frequencySets.get(count).remove(key);

        if(count == min && frequencySets.get(count).size() == 0)
            min++;

        if(!frequencySets.containsKey(newCount))
            frequencySets.put(newCount, new LinkedHashSet<>());

        frequencySets.get(newCount).add(key);

        return cache.get(key);
    }

    public void set(K key, V value) {

        if(cache.containsKey(key)) {
            // replace the value
            cache.put(key, value);
            // increase access status
            get(key);
            return;
        }

        if(cache.size() >= capacity) {
            // evict item
            K evict = frequencySets.get(min).iterator().next();
            frequencySets.get(min).remove(evict);
            cache.remove(evict);
            // don't remove from frequency map to count uses for a whole period
            // frequency.remove(evict);
        }

        cache.put(key, value);
        frequency.put(key, 1L);
        min = 1L;
        frequencySets.get(1L).add(key);
    }

    @Override
    public void printContent() {

        System.out.println("min="+min);
        System.out.println(cache);
        System.out.println(frequency);
        System.out.println(frequencySets);
        System.out.println("------------");
        
    }
}
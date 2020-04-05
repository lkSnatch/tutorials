package net.snatchTech.consistentHashing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache<K,V> {

    private final ConcurrentMap<K,V> cache;

    public Cache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void putValue(K key, V value) {
        cache.put(key, value);
    }

    public V getValue(K key) {
        return cache.get(key);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Cache: ");
        cache.forEach((k, v) -> {
            sb.append("(").append(k).append(", ").append(v).append(")").append(" ");
        });

        return sb.toString();
    }

}

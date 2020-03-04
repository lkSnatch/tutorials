package net.snatchTech.cacheEviction;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class CacheEvictionApp {

    public static void main(String[] args) {

        //Cache<Integer, String> cache = new LRUCache<>(4);
        //Cache<Integer, String> cache = new LFUCacheActiveMQ<>(4, 0.01F);
        Cache<Integer, String> cache = new LFUCacheThreeMaps<>(4);

        testCacheLFUThreeMaps(cache);

    }

    private static void testCacheLRU(Cache<Integer, String> cache) {
        // A B C D E B F E
        cache.printContent();
        cache.set(1, "A");
        cache.printContent();
        cache.set(2, "B");
        cache.printContent();
        cache.set(3, "C");
        cache.printContent();
        cache.set(4, "D");
        cache.printContent();
        cache.set(5, "E");
        cache.printContent();
        cache.get(2);
        cache.printContent();
        cache.set(6, "F");
        cache.printContent();
        cache.get(5);
        cache.printContent();
    }

    private static void testCacheLFUApache(Cache<Integer, String> cache) {
        // A B C D E B B B B B F E
        cache.printContent();
        cache.set(1, "A");
        cache.printContent();
        cache.set(2, "B");
        cache.printContent();
        cache.set(3, "C");
        cache.printContent();
        cache.set(4, "D");
        cache.printContent();
        cache.set(5, "E");
        cache.printContent();
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.printContent();
        cache.set(6, "F");
        cache.printContent();
        cache.get(5);
        cache.printContent();
    }

    private static void testCacheLFUThreeMaps(Cache<Integer, String> cache) {
        // A B C D E B B B B B F E
        cache.printContent();
        cache.set(1, "A");
        cache.printContent();
        cache.set(2, "B");
        cache.printContent();
        cache.set(3, "C");
        cache.printContent();
        cache.set(4, "D");
        cache.printContent();
        cache.set(5, "E");
        cache.printContent();
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.get(2);
        cache.printContent();
        cache.set(6, "F");
        cache.printContent();
        cache.get(5);
        cache.printContent();
    }

}

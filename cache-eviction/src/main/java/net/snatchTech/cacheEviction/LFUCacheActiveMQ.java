package net.snatchTech.cacheEviction;

import java.util.*;

/**
 * apache active mq implementation
 * https://svn.apache.org/repos/asf/activemq/trunk/activemq-kahadb-store/src/main/java/org/apache/activemq/util/LFUCache.java
 */
public class LFUCacheActiveMQ<Key, Value> implements Map<Key, Value>, Cache<Key, Value> {

    private final Map<Key, CacheNode<Key, Value>> cache;
    private final LinkedHashSet[] frequencyList;
    private int lowestFrequency;
    private int maxFrequency;
    //
    private final int maxCacheSize;
    private final float evictionFactor;

    public LFUCacheActiveMQ(int maxCacheSize, float evictionFactor) {
        if (evictionFactor <= 0 || evictionFactor >= 1) {
            throw new IllegalArgumentException("Eviction factor must be greater than 0 and lesser than or equal to 1");
        }
        this.cache = new HashMap<Key, CacheNode<Key, Value>>(maxCacheSize);
        this.frequencyList = new LinkedHashSet[maxCacheSize];
        this.lowestFrequency = 0;
        this.maxFrequency = maxCacheSize - 1;
        this.maxCacheSize = maxCacheSize;
        this.evictionFactor = evictionFactor;
        initFrequencyList();
    }

    public Value put(Key k, Value v) {
        Value oldValue = null;
        CacheNode<Key, Value> currentNode = cache.get(k);
        if (currentNode == null) {
            if (cache.size() == maxCacheSize) {
                doEviction();
            }
            LinkedHashSet<CacheNode<Key, Value>> nodes = frequencyList[0];
            currentNode = new CacheNode(k, v, 0);
            nodes.add(currentNode);
            cache.put(k, currentNode);
            lowestFrequency = 0;
        } else {
            oldValue = currentNode.v;
            currentNode.v = v;
        }
        return oldValue;
    }


    public void putAll(Map<? extends Key, ? extends Value> map) {
        for (Map.Entry<? extends Key, ? extends Value> me : map.entrySet()) {
            put(me.getKey(), me.getValue());
        }
    }

    public Value get(Object k) {
        CacheNode<Key, Value> currentNode = cache.get(k);
        if (currentNode != null) {
            int currentFrequency = currentNode.frequency;
            if (currentFrequency < maxFrequency) {
                int nextFrequency = currentFrequency + 1;
                LinkedHashSet<CacheNode<Key, Value>> currentNodes = frequencyList[currentFrequency];
                LinkedHashSet<CacheNode<Key, Value>> newNodes = frequencyList[nextFrequency];
                moveToNextFrequency(currentNode, nextFrequency, currentNodes, newNodes);
                cache.put((Key) k, currentNode);
                if (lowestFrequency == currentFrequency && currentNodes.isEmpty()) {
                    lowestFrequency = nextFrequency;
                }
            } else {
                // Hybrid with LRU: put most recently accessed ahead of others:
                LinkedHashSet<CacheNode<Key, Value>> nodes = frequencyList[currentFrequency];
                nodes.remove(currentNode);
                nodes.add(currentNode);
            }
            return currentNode.v;
        } else {
            return null;
        }
    }

    public Value remove(Object k) {
        CacheNode<Key, Value> currentNode = cache.remove(k);
        if (currentNode != null) {
            LinkedHashSet<CacheNode<Key, Value>> nodes = frequencyList[currentNode.frequency];
            nodes.remove(currentNode);
            if (lowestFrequency == currentNode.frequency) {
                findNextLowestFrequency();
            }
            return currentNode.v;
        } else {
            return null;
        }
    }

    public int frequencyOf(Key k) {
        CacheNode<Key, Value> node = cache.get(k);
        if (node != null) {
            return node.frequency + 1;
        } else {
            return 0;
        }
    }

    public void clear() {
        for (int i = 0; i <= maxFrequency; i++) {
            frequencyList[i].clear();
        }
        cache.clear();
        lowestFrequency = 0;
    }

    public Set<Key> keySet() {
        return this.cache.keySet();
    }

    public Collection<Value> values() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<Entry<Key, Value>> entrySet() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int size() {
        return cache.size();
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public boolean containsKey(Object o) {
        return this.cache.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private void initFrequencyList() {
        for (int i = 0; i <= maxFrequency; i++) {
            frequencyList[i] = new LinkedHashSet<CacheNode<Key, Value>>();
        }
    }

    private void doEviction() {
        int currentlyDeleted = 0;
        float target = maxCacheSize * evictionFactor;
        while (currentlyDeleted < target) {
            LinkedHashSet<CacheNode<Key, Value>> nodes = frequencyList[lowestFrequency];
            if (nodes.isEmpty()) {
                throw new IllegalStateException("Lowest frequency constraint violated!");
            } else {
                Iterator<CacheNode<Key, Value>> it = nodes.iterator();
                while (it.hasNext() && currentlyDeleted++ < target) {
                    CacheNode<Key, Value> node = it.next();
                    it.remove();
                    cache.remove(node.k);
                }
                if (!it.hasNext()) {
                    findNextLowestFrequency();
                }
            }
        }
    }

    private void moveToNextFrequency(CacheNode<Key, Value> currentNode, int nextFrequency, LinkedHashSet<CacheNode<Key, Value>> currentNodes, LinkedHashSet<CacheNode<Key, Value>> newNodes) {
        currentNodes.remove(currentNode);
        newNodes.add(currentNode);
        currentNode.frequency = nextFrequency;
    }

    private void findNextLowestFrequency() {
        while (lowestFrequency <= maxFrequency && frequencyList[lowestFrequency].isEmpty()) {
            lowestFrequency++;
        }
        if (lowestFrequency > maxFrequency) {
            lowestFrequency = 0;
        }
    }

    private static class CacheNode<Key, Value> {

        public final Key k;
        public Value v;
        public int frequency;

        public CacheNode(Key k, Value v, int frequency) {
            this.k = k;
            this.v = v;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return v + "=" + frequency;
        }
    }

    @Override
    public void set(Key key, Value value) {
        put(key, value);
    }

    @Override
    public void printContent() {

        System.out.println("low freq="+lowestFrequency);
        System.out.println("max freq="+maxFrequency);
        System.out.println(cache);
        System.out.println(Arrays.toString(frequencyList));
        System.out.println("------------");

    }
}
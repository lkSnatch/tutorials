package net.snatchTech.consistentHashing;

import java.util.concurrent.ConcurrentMap;

public class DecisionService<K,V> {

    private final ServerTree serverTree;
    private final ConcurrentMap<Server, Cache<K, V>> serverCache;

    public DecisionService(ServerTree serverTree, ConcurrentMap<Server, Cache<K, V>> serverCache) {
        this.serverTree = serverTree;
        this.serverCache = serverCache;
    }

    public void putValue(K key, V value) {

        int hashCode = key.hashCode() < 0 ? key.hashCode() * -1 : key.hashCode();
        // determine a server and put value to its cache
        Server server = serverTree.getServer(hashCode);
        Cache<K, V> cache = serverCache.get(server);
        cache.putValue(key, value);

    }

    public V getValue(K key) {

        int hashCode = key.hashCode() < 0 ? key.hashCode() * -1 : key.hashCode();
        // determine a server and get value from its cache
        Server server = serverTree.getServer(hashCode);
        Cache<K, V> cache = serverCache.get(server);
        return cache.getValue(key);

    }

}

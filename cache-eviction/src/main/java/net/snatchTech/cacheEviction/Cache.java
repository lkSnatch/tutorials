package net.snatchTech.cacheEviction;

public interface Cache<K,V> {

    V get(K key);

    void set(K key, V value);

    void printContent();

}

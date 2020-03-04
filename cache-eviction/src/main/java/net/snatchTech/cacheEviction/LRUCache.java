package net.snatchTech.cacheEviction;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K,V> implements Cache<K,V> {

    private static class Node<K,V> {
        K key;
        V item;
        Node<K,V> next;
        Node<K,V> prev;

        Node(Node<K,V> prev, K key, V element, Node<K,V> next) {
            this.key = key;
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return " " +
                    (prev == null ? null : prev.item) + " <<-- " +
                    item +
                    " -->> " + (next == null ? null : next.item);
        }
    }

    private final int capacity;
    private Node<K,V> head;
    private Node<K,V> tail;
    private final Map<K, Node<K,V>> cache;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    @Override
    public void set(K key, V value) {

        // try to find node with value in the cache
        Node<K,V> node = cache.get(key);

        if (node == null) {
            // create new one at the head
            node = new Node<>(head, key, value, null);
            cache.put(key, node);
        } else {
            node.item = value;
            connectNeighborNodes(node);
            ifThisNodeIsTail(node);
        }
        setHead(node);

        // remove tail
        if (tail != null && cache.size() > capacity) {
            cache.remove(tail.key);
            tail = tail.next;
            if (tail != null)
                tail.prev = null;
        } else if (tail == null) {
            tail = node;
        }

    }

    @Override
    public V get(K key) {

        Node<K,V> node = cache.get(key);
        if (node == null) {
            // cache miss
            return null;
        }

        connectNeighborNodes(node);
        ifThisNodeIsTail(node);
        setHead(node);

        return node.item;
    }

    private void connectNeighborNodes(Node<K, V> node) {
        // connect neighbor nodes between themselves
        if (node.prev != null)
            node.prev.next = node.next;
        if (node.next != null)
            node.next.prev = node.prev;
    }

    private void ifThisNodeIsTail(Node<K, V> node) {
        // change the tail
        if (tail == node)
            tail = node.next;
    }

    private void setHead(Node<K, V> node) {
        // set accessed element as the head
        node.prev = head;
        if (head != null)
            head.next = node;
        head = node;
        node.next = null;
    }

    @Override
    public void printContent() {

        System.out.println("head="+head);
        System.out.println(cache);
        System.out.println("tail="+tail);
        System.out.println("--------------");

    }
}

package com.angelo.gitapplication.redis;

import java.util.LinkedHashMap;

/**
 * author: Angelo.Luo
 * date : 08/30/2024 3:17 PM
 * description:
 */
public class LRUCacheDemo<K, V> extends LinkedHashMap<K, V> {

    private int capacity;


    public LRUCacheDemo(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    //用于判断是否需要删除最近最久未使用的节点
    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {

        LRUCacheDemo lruCacheDemo = new LRUCacheDemo(3);

        Object o = lruCacheDemo.get(1);

        lruCacheDemo.put(1, "a");
        lruCacheDemo.put(2, "b");
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());

        lruCacheDemo.put(4, "d");
        System.out.println(lruCacheDemo.keySet());

        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(3, "c");
        System.out.println(lruCacheDemo.keySet());
        lruCacheDemo.put(5, "x");
        System.out.println(lruCacheDemo.keySet());
    }

}

package com.angelo.gitapplication.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 06/05/2024 9:11 AM
 * description:Guava 淘汰策略（被动淘汰、主动淘汰）
 */
public class GuavaWeedOut {
    public static void main(String[] args) {
//        weedOut01();
//        weedOut02();
        weedOut03();
    }

    /**
     * 被动淘汰
     * 第一种：基于数据量（size或者weight）
     * FIFO的淘汰策略
     */
    public static void weedOut01() {
        Cache<String, String> build = CacheBuilder.newBuilder()
                .maximumSize(2)
                .removalListener(item -> System.out.println("移除的数据是：" + item))
                .build();
        build.put("key1", "value1");
        build.put("key2", "value2");
        build.put("key3", "value3");

        System.out.println("缓存数据数量：" + build.size());
    }

    /**
     * 被动淘汰
     * 第二种：基于过期时间（拆功时间、访问时间）
     * 第三种：基于引用：利用JVM的GC清除策略，没有被引用的对象进行清除；使用较少
     */
    public static void weedOut02() {
        Cache<String, String> build = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1L, TimeUnit.SECONDS)
                .recordStats()
                .concurrencyLevel(1)//强制所有的数据放在一个分片中（但是这样的话锁的粒度就会变大）
                .build();

        build.put("key1", "value1");
        build.put("key2", "value2");
        build.put("key3", "value3");
        build.put("key4", "value4");

        System.out.println("key1是否存在1：" + build.getIfPresent("key1"));
        System.out.println("第一次recordStats：" + build.stats());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("key1是否存在2：" + build.getIfPresent("key1"));
        /**
         * 通过size()查询的数量为1，但是通过asMap()查询的数据为{}；按理都过期应该被清除但是查询数量为1，主要是因为Guava Cache的过期数据淘汰是一种被动触发技能；
         * 在执行getIfPresent()方法的时候会每次会调用segment(分区)的方法get()，而又会触发cleanUp()方法对数据清除；
         * 但是数据清除是分段进行的，和ConcurrentHashMap一样采用了分段锁，执行getIfPresent()只会清除当前分段的过期数据，如果其他分期还存在过期数据是不会被清除，所有就会出现size不为0的情况；
         * 因为Guava Cache会在保证业务数据准确的情况下，尽可能的兼顾处理性能，在该清理的时候，自会去执行对应的清理操作，所以也无需过于担心。
         */
        System.out.println("缓存数量：" + build.size());
        System.out.println("all data：" + build.asMap());
        System.out.println("第二次recordStats：" + build.stats());
    }

    /**
     * 主动淘汰:将缓存立即将指定的记录给删除，调用删除方法就好；
     */
    public static void weedOut03() {
        Cache<Object, Object> build = CacheBuilder.newBuilder()
                .recordStats()
                .maximumSize(1000L)
                .build();
        build.put("key1", "value1");
        build.put("key2", "value2");
        build.put("key3", "value3");
        build.put("key4", "value4");
        build.put("key5", "value5");

        build.invalidate("key1");//删除指定记录
        System.out.println("cache data：" + build.asMap());

        build.invalidateAll(Arrays.asList("key2", "key3"));//批量删除给定记录
        System.out.println("cache data：" + build.asMap());

        build.invalidateAll();//清空整个缓存容器
        System.out.println("cache data：" + build.asMap());
    }
}

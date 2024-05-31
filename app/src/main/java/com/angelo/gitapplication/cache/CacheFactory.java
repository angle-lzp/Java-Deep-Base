package com.angelo.gitapplication.cache;

import java.util.HashMap;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 10:19 AM
 * description:
 */
public class CacheFactory {
    public static ICache createCache(CacheType cacheType) {
        //当前jdk版本为：17.0.7；所以可以使用这些新特性；
        return switch (cacheType) {
            case DEFAULT -> new DefaultCache();
            case LRU -> new LruCache();
        };
    }
}

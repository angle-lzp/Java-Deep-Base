package com.angelo.gitapplication.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 10:39 AM
 * description:
 */
public class CacheManager implements ICacheManager {

    private Map<String, ICache> caches = new ConcurrentHashMap<>();
    private List<ICacheHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public <K, V> ICache<K, V> getCache(String key, Class<K> keyType, Class<V> valueType) {
        try {
            return (ICache<K, V>) caches.get(key);
        } catch (Exception e) {
            throw new RuntimeException("failed to get cache", e);
        }
    }

    @Override
    public void createCache(String key, CacheType cacheType) {
        ICache cache = CacheFactory.createCache(cacheType);
        handlers.add((ICacheHandler) cache);
        caches.put(key, cache);
    }

    @Override
    public void destroyCache(String key) {

    }

    @Override
    public void destroyAllCache() {

    }

    @Override
    public Set<String> getAllCacheNames() {
        return null;
    }

    //定时清除机制
    public CacheManager() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start clean expired data timely");
                for (ICacheHandler handler : handlers) {
                    handler.clearAllExpiredCaches();
                }
            }
        }, 10000L, 24 * 60 * 60 * 1000L);//延迟60秒；每天定期清除；
    }
}
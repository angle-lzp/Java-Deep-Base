package com.angelo.gitapplication.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 10:24 AM
 * description:
 */
public class LruCache<K, V> implements ICache<K, V>, ICacheHandler<K> {

    Map<K, CacheItem> data = new HashMap<>();

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public void put(K key, V value, int timeIntvl, TimeUnit timeUnit) {

    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    @Override
    public Map<K, V> getAll(Set<K> keys) {
        return null;
    }

    @Override
    public void putAll(Map<K, V> map) {

    }

    @Override
    public void putAll(Map<K, V> map, int timeIntvl, TimeUnit timeUnit) {

    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        return false;
    }

    @Override
    public boolean putIfPresent(K key, V value) {
        return false;
    }

    @Override
    public void expireAfter(K key, int timeIntvl, TimeUnit timeUnit) {

    }

    //惰性删除
    @Override
    public void removeIfExpired(K key) {
        Optional.ofNullable(data.get(key)).map(item -> ((CacheItem) item).getExpired()).ifPresent(expired -> {
            if (expired) {
                data.remove(key);
            }
        });
    }

    //定期删除
    @Override
    public void clearAllExpiredCaches() {
        List<String> collect =
                (List<String>) data.entrySet().stream().filter(item -> item.getValue().getExpired()).map(Map.Entry::getKey).collect(Collectors.toList());
        collect.forEach(item -> data.remove(item));
    }
}

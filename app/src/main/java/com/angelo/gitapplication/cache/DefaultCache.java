package com.angelo.gitapplication.cache;

import java.time.LocalDateTime;
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
public class DefaultCache<K, V> implements ICache<K, V>, ICacheHandler<K> {

    Map<K, CacheItem> data = new HashMap<>();

    @Override
    public Optional get(K key) {
        removeIfExpired(key);
        return Optional.ofNullable(data.get(key)).map(CacheItem::getValue);
    }

    @Override
    public void put(K key, V value) {
        data.put(key, new CacheItem<V>(value, null));
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
        if (timeIntvl <= 0) {
            throw new RuntimeException("时间间隔不能小于1！");
        }
        CacheItem cacheItem = data.get(key);
        //设置过期时间
        cacheItem.setExpiredTime(LocalDateTime.now().plusSeconds(timeUnit.toSeconds(timeIntvl)));
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

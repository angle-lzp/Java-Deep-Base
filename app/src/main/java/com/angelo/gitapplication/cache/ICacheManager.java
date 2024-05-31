package com.angelo.gitapplication.cache;

import java.util.Set;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 9:46 AM
 * description:
 */
public interface ICacheManager {
    //获取指定的缓存容器
    <K, V> ICache<K, V> getCache(String key, Class<K> keyType, Class<V> valueType);

    //创建一个新的缓存容器
    void createCache(String key, CacheType cacheType);

    //销毁指定的缓存容器
    void destroyCache(String key);

    //销毁所有的缓存容器
    void destroyAllCache();

    //获取所有的缓存容器名称
    Set<String> getAllCacheNames();


}

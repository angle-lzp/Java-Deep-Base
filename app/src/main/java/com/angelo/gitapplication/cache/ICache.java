package com.angelo.gitapplication.cache;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 9:17 AM
 * description:缓存接口
 */
public interface ICache<K, V> {
    //根据key查询对应的值
    Optional<V> get(K key);

    //将对应的记录添加到缓存中
    void put(K key, V value);

    //重载方法，增加过期时间的参数设定
    void put(K key, V value, int timeIntvl, TimeUnit timeUnit);

    //将指定的缓存记录删除
    V remove(K key);

    //判断缓存中是否有指定的值
    boolean containsKey(K key);

    //清空缓存
    void clear();

    //判断缓存中是否有指定的值
    boolean containsValue(V value);

    //传入多个key，然后批量查询各个key对应的值，批量返回，提升调用方的使用效率
    Map<K, V> getAll(Set<K> keys);

    //一次性批量将多个键值对添加到缓存中，提升调用方的使用效率
    void putAll(Map<K, V> map);

    //重载方法，增加过期时间的参数设定
    void putAll(Map<K, V> map, int timeIntvl, TimeUnit timeUnit);

    //如果不存在的情况下则添加到缓存中，如果存在则不做操作
    boolean putIfAbsent(K key, V value);

    //如果key已存在的情况下则去更新key对应的值，如果不存在则不做操作
    boolean putIfPresent(K key, V value);

    //用于指定某个记录的过期时间长度
    void expireAfter(K key, int timeIntvl, TimeUnit timeUnit);

}

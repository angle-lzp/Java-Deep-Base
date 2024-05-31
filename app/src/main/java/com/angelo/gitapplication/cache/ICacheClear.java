package com.angelo.gitapplication.cache;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 9:27 AM
 * description:
 */
public interface ICacheClear<K> {
    //惰性删除(查询它的时候过期了把他删除)-如果给定的key过期则直接删除
    void removeIfExpired(K key);

    //定期删除(每隔一段时间删除过期的所有数据)-清除当前容器中已经过期的所有缓存记录
    void clearAllExpiredCaches();
}

package com.angelo.gitapplication.cache;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 11:40 AM
 * description:
 */
public class CacheItem<V> {
    private V value;
    private LocalDateTime expiredTime;

    public CacheItem(V value, LocalDateTime expiredTime) {
        this.value = value;
        this.expiredTime = expiredTime;
    }

    public boolean getExpired() {
        return Optional.ofNullable(expiredTime).map(t -> LocalDateTime.now().isAfter(t)).orElse(false);

    }

    public V getValue() {
        return value;
    }

    public CacheItem setValue(V value) {
        this.value = value;
        return this;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public CacheItem setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
        return this;
    }
}

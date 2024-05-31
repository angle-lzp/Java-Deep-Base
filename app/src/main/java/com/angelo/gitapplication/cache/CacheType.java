package com.angelo.gitapplication.cache;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 9:51 AM
 * description:
 */
public enum CacheType {
    DEFAULT(DefaultCache.class),
    LRU(LruCache.class);
    private Class<? extends ICache> classType;

    CacheType(Class<? extends ICache> classType) {
        this.classType = classType;
    }

    public Class<? extends ICache> classType() {
        return classType;
    }
}

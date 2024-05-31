package com.angelo.gitapplication.cache;

import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 2:51 PM
 * description:
 */
public class UseCacheMain {
    public static void main(String[] args) throws InterruptedException {
        ICacheManager manager = new CacheManager();
        manager.createCache("userData", CacheType.DEFAULT);
        ICache<String, User> userDataCache = manager.getCache("userData", String.class, User.class);
        userDataCache.put("user1", new User("angelo"));
        userDataCache.expireAfter("user1", 10, TimeUnit.SECONDS);
        userDataCache.get("user1").ifPresent(value -> System.out.println("找到用户：" + value));

        Thread.sleep(12000L);

        System.out.println("找到用户：" + userDataCache.get("user1"));
    }
}

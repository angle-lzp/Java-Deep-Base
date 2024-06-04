package com.angelo.gitapplication.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 06/04/2024 8:50 AM
 * description:
 */
public class GuavaCacheDemo {
    public static void main(String[] args) {
        System.out.println(Math.sqrt(4D));
        //三角函数
        System.out.println(Math.cos(90D));
        //使用缓存demo
        try {
            Cache<String, User> userCache01 = createUserCache01();
            //userCache01.put("luo", new User("angelo", "1234"));
            User user = userCache01.get("luo", () -> new User("lzp", "000"));//数据回填方式一：实现Callable接口
            User user1 = userCache01.get("data", () -> new User("data", "callable"));//在Callable缺失的情况下才会执行CacheLoader（问题是这个Callable还是必须的）
            System.out.println(user);
            System.out.println(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 基于创建时间过期
     *
     * @return
     */
    public static Cache<String, User> createUserCache01() {
        return CacheBuilder.newBuilder()
                .recordStats()//开启缓存行为的统计记录
                .expireAfterWrite(30L, TimeUnit.MINUTES)
                .build(new CacheLoader<String, User>() {//数据回填方式二：实现CacheLoader接口
                    @Override
                    public User load(String key) throws Exception {
                        return new User("data", "999");
                    }
                });
    }

    /**
     * 基于最后访问时间过期
     *
     * @return
     */
    public static Cache<String, User> createUserCache02() {
        return CacheBuilder.newBuilder()
                .expireAfterAccess(30L, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 限制缓存记录条数
     *
     * @return
     */
    public static Cache<String, User> createUserCache03() {
        return CacheBuilder.newBuilder()
                .maximumSize(10000L)//设置缓存容量的最大数量值
                .build();
    }

    /**
     * 限制缓存记录权重
     *
     * @return
     */
    public static Cache<String, User> createUserCache04() {
        return CacheBuilder.newBuilder()
                .maximumWeight(10000L)//最大的权重数
                .weigher((key, value) -> Math.abs(value.hashCode()) / 1024)//ceil:向上取整;abs:绝对值;这里只是随机举个例子
                .build();
    }

}

class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

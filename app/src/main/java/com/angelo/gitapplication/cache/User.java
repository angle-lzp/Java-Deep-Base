package com.angelo.gitapplication.cache;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 2:52 PM
 * description:
 */
public class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String setUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

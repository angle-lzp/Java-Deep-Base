package com.angelo.gitapplication.nio.chat;

/**
 * author: Angelo.Luo
 * date : 06/04/2024 5:18 PM
 * description:
 */
public class Client02 {
    public static void main(String[] args) {
        new ClientChannel().start("小华");
    }
}
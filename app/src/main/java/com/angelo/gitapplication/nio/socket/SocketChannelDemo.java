package com.angelo.gitapplication.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 3:01 PM
 * description:客户端SocketChannel 创建SocketChannel的方式
 */
public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //方式一：
        SocketChannel socketChannel1 = SocketChannel.open();
        socketChannel1.connect(new InetSocketAddress("localhost", 9090));

        //方式二：
        SocketChannel socketChannel2 = SocketChannel.open(new InetSocketAddress("localhost", 9090));
    }
}

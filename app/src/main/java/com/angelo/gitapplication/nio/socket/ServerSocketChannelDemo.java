package com.angelo.gitapplication.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 2:27 PM
 * description:ServerSocketChannel
 */
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定socket的端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9090));
        //设置为非阻塞（默认是阻塞）
        serverSocketChannel.configureBlocking(false);
        while (true) {
            System.out.println("等待客户端连接");
            //这里获取的是服务端的SocketChannel(和客户端的SocketChannel是一样都是使用它来进行通信的)
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println(socketChannel.getRemoteAddress());///127.0.0.1:53589(客户端)
                System.out.println(socketChannel.getLocalAddress());///127.0.0.1:9090(服务端)
            } else {
                System.out.println("继续等待...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

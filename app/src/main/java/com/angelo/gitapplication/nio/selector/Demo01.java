package com.angelo.gitapplication.nio.selector;

import androidx.compose.foundation.text.selection.Selectable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 2:52 PM
 * description:
 */
public class Demo01 {
    public static void main(String[] args) throws IOException {
        //创建Selector
        Selector selector = Selector.open();
        //创建channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //设置非阻塞
        channel.configureBlocking(false);
        //设置端口
        channel.bind(new InetSocketAddress(9001));
        //进行注册:将channel注册到selector中，并对【接收连接】这个状态进行监听
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }
}

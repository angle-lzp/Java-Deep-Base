package com.angelo.gitapplication.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 3:54 PM
 * description:
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9001));
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap("客户端发送过来的数据".getBytes()));
        channel.close();
    }
}

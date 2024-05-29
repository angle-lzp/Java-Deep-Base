package com.angelo.gitapplication.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 3:01 PM
 * description:客户端SocketChannel
 */
public class SocketChannelDemo2 {
    public static void main(String[] args) throws IOException {
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        //设置非阻塞（默认是阻塞的）
        socketChannel.configureBlocking(false);
        //客户端连接装填
        /*socketChannel.isConnected();//已连接（表示连接结束了）
        socketChannel.isConnectionPending();//连接中
        socketChannel.isOpen();//打开中*/

        if (socketChannel.isConnectionPending()) {//是否处于连接中的状态
            socketChannel.finishConnect();//结束连接
        }

        //设置/获取参数
        socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 1024);//接收数据大小
        socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 1024);//发送数据大小

        socketChannel.getOption(StandardSocketOptions.SO_SNDBUF);//获取发送数据大小的值

        //创建buffer读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(buffer);
        if (len == 0) {
            System.out.println("没有读取到数据");
        } else if (len == -1) {
            System.out.println("数据读取完成");
        } else {
            System.out.println(new String(buffer.array(), 0, len));
        }


    }
}

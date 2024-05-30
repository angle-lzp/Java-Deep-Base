package com.angelo.gitapplication.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 2:56 PM
 * description:
 */
public class Demo02 {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(9002));
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //在没有客户端操作的情况下该方法会阻塞，没有客户端连接肯定就是阻塞的嘛；
            //等待某个操作就绪状态的channel
            selector.select();
            //获得一个集合，里面包含了这次selector执行select方法获得的发送就绪状态的多个channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    //处理接收的业务
                } else if (key.isConnectable()) {
                    //处理连接的业务
                } else if (key.isReadable()) {
                    //处理读取的业务
                } else if (key.isWritable()) {
                    //处理写入的业务
                }
                //处理完对应的业务后，将该key进行移除，防止在下次对应的channel继续操作，但是因为上一次channel的操作没有移除，表示没有被处理；
                //那么下一次便不会获取你当次的操作，也不会进行处理；
                iterator.remove();
            }

        }
    }
}

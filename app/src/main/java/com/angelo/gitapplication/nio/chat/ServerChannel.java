package com.angelo.gitapplication.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 4:00 PM
 * description:服务端:将channel注册到Selector中，只是当前注册了；例如：我当前注册了一个channel监听它读的操作，在我读取了这个channel中的数据后
 * 需要再次注册，在下一次读监听的时候才可以进行读取到；在原来selectKeys中的也应该将当前的selectionKey移除，否则即使再次注册了也不会被加监听到；
 */
public class ServerChannel {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9001));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//这个多个客户端连接了，只注册了一次；搞不懂；
        while (true) {
            //等待客户端连接
            int select = selector.select();
            if (select == 0) {
                System.out.println("没有客户端连接！");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    private static void handleRead(SelectionKey key) {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            socketChannel.configureBlocking(false);
            Selector selector = key.selector();
            //读取数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int len = 0;
            String msg = "";
            while ((len = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                msg += new String(buffer.array(), 0, len, StandardCharsets.UTF_8);
                buffer.clear();
            }
            System.out.println(socketChannel.getRemoteAddress() + " - message:" + msg);
            //将当前数据进行广播
            handleBroadcast(key, msg);
            //将channel再次注册到selector上，让selector对read感兴趣
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 进行广播
     *
     * @param key
     */
    private static void handleBroadcast(SelectionKey key, String msg) {
        Selector selector = key.selector();
        Set<SelectionKey> selectionKeys = selector.keys();//获取所有的通道
        //selector.selectedKeys()//获取所有的被监听的channel，如果已经被移除了就不存在了，所以如果需要获取所有的channel需要使用keys()方法；
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        try {
            while (iterator.hasNext()) {
                SelectionKey value = iterator.next();
                SelectableChannel channel = value.channel();
                if (channel instanceof SocketChannel && channel != key.channel()) {
                    ((SocketChannel) channel).write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理获取服务端连接的请求
     *
     * @param key
     */
    private static void handleAccept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            Selector selector = key.selector();
            //将socketChannel这个通道的被读取操作添加到selector中；
            //通俗点：服务端通过selector监听socketChannel这个通道的读取操作（socketChannel已经写入了数据）
            //主导地位是服务端，所以要以服务端的视角来看待这些注册
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

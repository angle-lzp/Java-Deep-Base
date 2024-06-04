package com.angelo.gitapplication.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 4:01 PM
 * description:客户端
 */
public class ClientChannel {
    public void start(String name) {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9001));
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
            //多线程用户输入数据
            new ClientThread(socketChannel, name).start();
            while (true) {
                int select = selector.select();
                if (select == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        String msg = "";
                        while ((len = channel.read(buffer)) > 0) {
                            buffer.flip();
                            msg += new String(buffer.array(), 0, len, StandardCharsets.UTF_8);
                            buffer.clear();
                        }
                        System.out.println(((SocketChannel) key.channel()).getLocalAddress().toString() + " - " + msg);
                        //重新注册读的操作到selector上
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 写入数据
 */
class ClientThread extends Thread {

    private SocketChannel channel;
    private String name;

    public ClientThread(SocketChannel channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                String sendMsg = name + " : " + msg;
                if (msg.length() > 0) {
                    channel.write(ByteBuffer.wrap(sendMsg.getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

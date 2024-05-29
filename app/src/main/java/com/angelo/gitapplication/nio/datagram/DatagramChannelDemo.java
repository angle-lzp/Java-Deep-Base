package com.angelo.gitapplication.nio.datagram;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 3:52 PM
 * description:DatagramChannel
 */
public class DatagramChannelDemo {

    //客户端
    @Test
    public void sendInfo() throws IOException {
        //创建DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        //创建Socket连接地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9001);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("罗马可".getBytes());
        //翻转buffer（因为在执行ByteBuffer.allocate(1024)的时候，会将position设置为0 ，limit设置为1024；在进行put的时候添加数据position会累加）
        //执行了flip执行的操作：limit=position;position=0;
        buffer.flip();
        //在发送数据的时候：是从position位置开发发送，直到position<limit停止发送；这也是为什么需要执行上面的flip()方法的原因；
        channel.send(buffer, address);
        //关闭资源
        channel.close();
    }

    //客户端(用于替换sendInfo中buffer总是使用flip()方法的情况)
    @Test
    public void sendInfo2() throws IOException {
        //创建DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        //创建Socket连接地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9001);
        ByteBuffer buffer = ByteBuffer.wrap("Angelo.luo".getBytes());
        //发送数据
        channel.send(buffer, address);
        //关闭资源
        channel.close();
    }

    @Test
    public void receiveInfo() throws IOException {
        //创建DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        //创建Socket连接地址
        InetSocketAddress address = new InetSocketAddress(9001);
        //绑定Socket地址
        channel.bind(address);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            channel.receive(buffer);
            buffer.flip();
            System.out.println("客户端发送的数据：" + new String(buffer.array(), 0, buffer.limit()));
        }
    }

    /**
     * 使用DatagramChannel的read和write
     * 自己即使服务器也是客户端
     */
    @Test
    public void readAndWrite() throws IOException {
        //创建DatagramChannel
        DatagramChannel channel = DatagramChannel.open();
        //绑定端口
        channel.bind(new InetSocketAddress(9002));
        //连接
        channel.connect(new InetSocketAddress("127.0.0.1", 9002));
        //写数据
        channel.write(ByteBuffer.wrap("read write".getBytes()));
        //读数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            System.out.println("读取的数据是：" + new String(buffer.array(), 0, buffer.limit()));
        }
    }
}
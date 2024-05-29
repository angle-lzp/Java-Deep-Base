package com.angelo.gitapplication.nio.disagg;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 4:52 PM
 * description:分散和聚集
 * 分散：一个channel中的数据被多个buffer接收在程序中展示；       文件->一个channel->多个buffer->程序
 * 聚集：程序中多个buffer的数据聚集在一个channel中写到文件中；    程序->多个buffer->一个channel->文件
 */
public class DisAndAggDemo {

    public static void main(String[] args) throws IOException {
//        dispersion();
        aggregation();
    }

    //分散
    public static void dispersion() throws IOException {
        RandomAccessFile file = new RandomAccessFile("4.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(5);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = new ByteBuffer[]{buffer1, buffer2};
        while (channel.read(buffers) != -1) {
        }
        buffer1.flip();
        System.out.println("buffer1:" + new String(buffer1.array(), 0, buffer1.limit()));
        buffer2.flip();
        System.out.println("buffer1:" + new String(buffer2.array(), 0, buffer2.limit()));
        channel.close();
        file.close();
    }

    //聚集
    public static void aggregation() throws IOException {
        RandomAccessFile file = new RandomAccessFile("5.txt", "rw");
        FileChannel channel = file.getChannel();
        channel.write(new ByteBuffer[]{ByteBuffer.wrap("我是第一个buffer".getBytes()),ByteBuffer.wrap("angelo.luo".getBytes())});
        channel.close();
        file.close();
    }
}

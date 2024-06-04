package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 11:48 AM
 * description:异步读取数据方式一：
 */
public class AsynchronousFileFileChannelDemo01 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("7.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(10);
        long count = 0;
        while (true) {
            //读取后会立马返回一个future，也可能还没有读取完成；所以需要isDone()方法判断是否已经读取完成了，没有读取完成可以去执行其他的操作；这就是异步读取文件的情况；
            //第二个参数表示从文件中开始读取的位置；
            Future<Integer> future = fileChannel.read(buffer, count);
            //isDone:是否读取完成
            while (!future.isDone()) {
                //这里可以执行其他的操作
                System.out.println("================执行了其他的事情=====================");
            }
            if (future.get() == -1) {
                fileChannel.close();
                break;
            }
            buffer.flip();
            System.out.println("data:" + new String(buffer.array(), 0, buffer.limit()));
            System.out.println("------------------------------------------------------");
            count += buffer.limit();
            buffer.clear();
        }
        System.out.println("读取完成！");
    }
}

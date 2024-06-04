package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 11:48 AM
 * description:异步写入数据方式一：
 */
public class AsynchronousFileFileChannelDemo03 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("8.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.wrap("instanceof 增强的东西".getBytes());
        Future<Integer> future = fileChannel.write(buffer, 0);
        //没有写完可以执行其他的操作，写操作是异步的
        while (!future.isDone()) {
            System.out.println("other task");
        }
        System.out.println("写入完成！");
        fileChannel.close();
    }
}

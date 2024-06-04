package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 11:48 AM
 * description:异步读取数据方式二：
 */
public class AsynchronousFileFileChannelDemo02 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("8.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1);
        fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            long newCount = 0;

            //表示当前的一次读取完成了
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result != -1) {
                    System.out.println("result:" + result);
                    buffer.flip();
                    System.out.println("data:" + new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                    newCount += buffer.limit();
                    fileChannel.read(buffer, newCount, buffer, this);
                } else {
                    //文件中的数据已经全部读取完成
                    System.out.println("文件中的数据已经全部读取完成:" + result);
                }
            }

            //读取失败后的操作
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("读取失败:" + exc.getMessage());
            }
        });

        TimeUnit.SECONDS.sleep(60 * 2);
        System.out.println("读取完成！");
    }
}

package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

/**
 * author: Angelo.Luo
 * date : 06/03/2024 11:48 AM
 * description:异步写入数据方式二：
 */
public class AsynchronousFileFileChannelDemo04 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("8.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.wrap("instanceof 增强的东西".getBytes());
        fileChannel.write(buffer, fileChannel.size(), buffer, new CompletionHandler<Integer, ByteBuffer>() {
            //表示当前buffer数据写入成功
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("写入完成！");
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
        fileChannel.close();
        System.out.println("写入成功！");
    }
}

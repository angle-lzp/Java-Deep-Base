package com.angelo.gitapplication.nio.other.pipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 5:18 PM
 * description:
 */
public class FileLockDemo2 {
    public static void main(String[] args) throws IOException {
        //排它锁
        File file = new File("1.txt");
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel channel = fos.getChannel();
        FileLock lock = channel.lock();
        channel.write(ByteBuffer.wrap("写入数据".getBytes()));
        lock.release();
        channel.close();
        fos.close();
        System.out.println("写入完成！");

        //共享锁
/*        File file = new File("1.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        FileLock lock = channel.lock(0, file.length(), true);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array(), 0, buffer.limit()));
        buffer.clear();
        lock.release();
        channel.close();
        fis.close();*/
    }
}

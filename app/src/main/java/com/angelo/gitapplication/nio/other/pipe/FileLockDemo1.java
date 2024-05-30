package com.angelo.gitapplication.nio.other.pipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 5:06 PM
 * description:
 * 排它锁：可读可写；一个进程获取了这个排它锁其他进程就不能再获取了；
 * 共享锁：只能读；所有进程都可以获取这个锁；
 */
public class FileLockDemo1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        //演示排它锁
        File file = new File("1.txt");
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel channel = fos.getChannel();
        //方式一：阻塞
        //默认就是排它锁：lock(0L, Long.MAX_VALUE, false);
        FileLock lock = channel.lock();

        Thread.sleep(100000L);
        //一定要释放锁
        lock.release();

        //方式二：阻塞
        //第一第二个参数表示文件的起始位置到多长的长度的这段内容进行加锁；第三个形参：是否是共享锁；true：是；false：不是；
        //FileLock lock = channel.lock(0, file.length(), false);

        //方式三：非阻塞（不同的是如果没有获取到锁直接返回null）
        //默认获取排它锁：tryLock(0L, Long.MAX_VALUE, false)
        //FileLock tryLock = channel.tryLock();

        //方式四：非阻塞（不同的是如果没有获取到锁直接返回null）
        //参数介绍：和方式二中的作用是一样的
        //FileLock tryLock = channel.tryLock(0L, Long.MAX_VALUE, false);

        //演示共享锁
/*        File file = new File("1.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        FileLock lock = channel.lock(0, file.length(), true);
        Thread.sleep(100000L);
        lock.release();
        System.out.println("共享锁结束1");*/
    }
}

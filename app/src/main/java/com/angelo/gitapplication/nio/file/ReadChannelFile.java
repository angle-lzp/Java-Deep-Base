package com.angelo.gitapplication.nio.file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 10:35 AM
 * description:
 */
public class ReadChannelFile {
    public static void main(String[] args) throws Exception {
        //创建随机访问流
        RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
        //创建channel
        FileChannel channel = file.getChannel();
        /**
         * Java程序 -> buffer -> channel -> 文件
         * 注意：channel中的position和buffer中的position是独立的
         * 两个都是表示读取的起始位置，这点是没有错的
         * 一个存在于：channel通道中
         * 一个存在于：buffer缓冲中
         */

        /*//1.获取通道的开始位置
        System.out.println("通道起始位置："+channel.position());
        //2.设置position的值，表示从哪里开始读取;设置为1表示从下标1开始读取，会忽略第一个字节；
        channel.position(1);*/

        /*//截取字符串的长度：5：表示只读取5个字节0-4；
        channel.truncate(5);*/

        //创建buffer,为其分配对应的内存大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //读取数据
        int len = -1;
        while ((len = channel.read(buffer)) != -1) {
            //channel将数据写到buffer中，此时position记录的位置是buffer中数据的最后一位的位置(类似于length-1)
            //limit记录的是这个buffer中的数据最大长度
            //而进行读取的时候应该是从position=0的位置进行读取；
            //flip的作用就是将position的位置设置未0，limit设置为原position的值(没有设置为0之前position的值为buffer中的最后元素的下标值也就是最大值)
            buffer.flip();
            //读取数据
            while (buffer.hasRemaining()) {
                byte val = buffer.get();
                System.out.print((char) val);
            }
            //清空buffer中的数据,因为在上面while中通过get获取数据的时候每获取一个数据position就会累加，所以下面需要将buffer中的position设置为0
            buffer.clear();
        }
        //读取结束后关闭文件流
        file.close();
    }
}

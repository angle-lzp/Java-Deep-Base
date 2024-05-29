package com.angelo.gitapplication.nio.file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 11:18 AM
 * description:
 */
public class WriteChannelFile {
    public static void main(String[] args) throws Exception{
        //创建随机访问流
        RandomAccessFile file = new RandomAccessFile("2.txt","rw");
        //获取通道
        FileChannel channel = file.getChannel();
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将数存入buffer中
//        buffer.put("look up".getBytes());
        buffer.put("罗智鹏".getBytes());
        //此时还需要使用flip，因为上面put的话将数据存入到buffer中，此时的position的值为“p”的下标位置，那么在写入到文件中的时候是从“p”这个下标开始向后面读取limit个写入
        //此时的limit在初始化的时候是设置了1024，那么存入文件中的都是null
        //翻转buffer
        buffer.flip();
        //通过channel将buffer中的数据写入到文件中
        channel.write(buffer);
        //关闭
        file.close();
    }
}

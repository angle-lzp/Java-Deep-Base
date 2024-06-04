package com.angelo.gitapplication.nio.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 11:47 AM
 * description:基于映射缓冲区：直接调用系统底层的缓存，没有JVM和系统之间的复制操作，效率更高，主要用于操作大文件；
 */
public class Demo04 {
    public static void main(String[] args) throws IOException {
        File file = new File("5.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //通过channel获取文件的大小单位：字节Byte
        long size = channel.size();
        //此时已经读取到了文件中的数据了
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, file.length());

        //如果是非汉字就是一个字节一个字节输出
        /*while (mappedByteBuffer.hasRemaining()) {
            System.out.print((char) mappedByteBuffer.get());
        }*/

        //有汉字，进行编码
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        CharBuffer charBuffer = decoder.decode(mappedByteBuffer);
        System.out.println(charBuffer.toString());
        randomAccessFile.close();
    }

}

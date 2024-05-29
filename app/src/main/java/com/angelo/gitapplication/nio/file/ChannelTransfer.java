package com.angelo.gitapplication.nio.file;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * author: Angelo.Luo
 * date : 05/29/2024 11:45 AM
 * description:
 */
public class ChannelTransfer {
    public static void main(String[] args) throws Exception{
        //创建随机访问流
        RandomAccessFile srcFile = new RandomAccessFile("1.txt","rw");
        FileChannel srcChannel = srcFile.getChannel();

        RandomAccessFile destFile = new RandomAccessFile("4.txt","rw");
        FileChannel destChannel = destFile.getChannel();

        //transferFrom:输入到destChannel的数据源从哪里来；srcChannel=》destChannel
//        destChannel.transferFrom(srcChannel,0,srcChannel.size());

        //transferTo:当前这个srcChannel数据源到哪里去；srcChannel=》destChannel
        srcChannel.transferTo(0,srcChannel.size(),destChannel);

        srcFile.close();
        destFile.close();
    }
}

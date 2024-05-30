package com.angelo.gitapplication.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 11:38 AM
 * description:直接缓冲区
 */
public class Demo03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("1.txt");
        FileChannel fisChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("6.txt");
        FileChannel fosChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1);//直接allocateDirect缓冲区
        while (fisChannel.read(buffer) > 0){
            buffer.flip();//是limit=position；position=0；
            fosChannel.write(buffer);
            buffer.clear();//清空缓冲中的数据，才可以进行写入数据；position=0；limit=capacity；mark=-1;
        }
        fos.close();
        fis.close();
    }
}

package com.angelo.gitapplication.nio.buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 11:27 AM
 * description:子缓冲
 */
public class Demo01 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        //需要先设置子缓冲的区域：从下标2开始读取到下标5结束(不包括5)
        buffer.position(2);
        buffer.limit(5);
        //创建子缓冲
        ByteBuffer slice = buffer.slice();
        //修改子缓冲区的数据父缓冲也会被影响
        slice.put(0, (byte) 40);
        System.out.println(Arrays.toString(buffer.array()));
    }
}

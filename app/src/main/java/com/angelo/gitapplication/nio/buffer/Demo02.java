package com.angelo.gitapplication.nio.buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 11:34 AM
 * description:只读缓冲
 */
public class Demo02 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //修改只读缓冲去抛出异常：java.nio.ReadOnlyBufferException
        //readOnlyBuffer.put(0,(byte) 50);
        buffer.put(0,(byte) 100);
        System.out.println(Arrays.toString(buffer.array()));
    }
}



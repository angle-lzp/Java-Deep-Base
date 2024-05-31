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

        //System.out.println(Optional.empty().get());//NoSuchElementException:No value present
        //System.out.println(Optional.of(null).get());//NullPointerException
        //System.out.println(Optional.ofNullable(null).get());//NoSuchElementException:No value present

        //Optional.ofNullable("string").map(String::toUpperCase);
        //System.out.println(Optional.ofNullable("string").flatMap(value -> Optional.ofNullable(value.toUpperCase())).orElse("No such value"));

        //如果为null不存在（isPresent()=false）,就不会执行map里边的接口函数；以及返回原来的值；
/*        System.out.println(Optional.ofNullable(null).map(item -> {
            System.out.println("111");
            return "CCC";
        }));*/
    }
}

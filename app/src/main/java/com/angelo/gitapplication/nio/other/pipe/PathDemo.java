package com.angelo.gitapplication.nio.other.pipe;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 5:36 PM
 * description:
 */
public class PathDemo {
    public static void main(String[] args) {
        //相对路径
        Path path = Paths.get("1.txt");
        System.out.println("是否是绝对路径:" + path.isAbsolute());

        //绝对路径
        Path path1 = Paths.get("C:\\apps\\androidFramework\\GitApplication\\1.txt");
//        Path path1 = Paths.get("C:\\apps\\androidFramework\\GitApplication\\app\\4.txt");

//        Path path2 = Paths.get("C:\\apps\\androidFramework\\GitApplication\\app", "4.txt");
        System.out.println("是否是绝对路径:" + path1.isAbsolute());

        //Path的相关方法
        System.out.println(path1.getFileSystem());//文件系统
        System.out.println(path1.getName(1));//路径中第二层目录的名称（从下标0开始）androidFramework
        System.out.println(path1.getFileName());//当前文件的名称
        System.out.println(path1.getParent());//父绝对路径（除去当前文件名称：C:\apps\androidFramework\GitApplication）
        System.out.println(path1.getRoot());//路径的根（C:\）
        System.out.println(path1.getNameCount());//表示整个路径的中的长度（除了根C:\；
        // 比如：C:\apps\androidFramework 就是2，C:\apps\androidFramework\GitApplication\app\4.txt 就是5）
    }
}

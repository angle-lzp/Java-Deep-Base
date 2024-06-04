package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

/**
 * author: Angelo.Luo
 * date : 05/31/2024 4:39 PM
 * description:
 */
public class FilesDemo {
    public static void main(String[] args) throws IOException {
        /*Path path = Paths.get("myDirectory");
        Files.createDirectories(path);*///文件夹已经存在再次创建不会抛出异常

        //这个还不行
        /*Path path1 = Paths.get("myDirectory2", "1.txt");
        Files.createFile(path1);*/

        /*Path path2 = Paths.get("temp.txt");
        Files.createFile(path2);*///已经存在再次创建会抛异常：FileAlreadyExistsException

        /*Files.copy((Path) null, null, StandardCopyOption.REPLACE_EXISTING);//可以覆盖
        Files.move(null, null);//可通过这个来给文件修改名字
        Files.delete(null);//删除文件*/

        //遍历目录
        Path path = Paths.get("myDirectory");
        Files.walkFileTree(path,new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return super.visitFileFailed(file, exc);
            }
        });



    }
}

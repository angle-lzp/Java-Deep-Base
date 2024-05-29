package com.angelo.gitapplication.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * author: Angelo.Luo
 * date : 05/28/2024 2:32 PM
 * description:客户端
 */
public class ServerClient2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);
        //先给服务端发送数据
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("I`m client send info 2 !".getBytes());
        outputStream.flush();
        //获取客户端输入流，读取通道中的数据
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println("服务器发送的数据：" + new String(bytes, 0, len));
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}

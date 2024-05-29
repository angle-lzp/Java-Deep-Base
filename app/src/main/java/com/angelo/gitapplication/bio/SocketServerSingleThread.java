package com.angelo.gitapplication.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author: Angelo.Luo
 * date : 05/28/2024 1:52 PM
 * description:单线程服务端
 */
public class SocketServerSingleThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            //服务端获取连接
            System.out.println("等待客户端连接...");
            Socket socket = serverSocket.accept();//等待客户端连接会被阻塞
            //读取通道中的数据
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);//读取数据也会被阻塞
            System.out.println("客户端发送的数据：" + new String(bytes, 0, len));
            //给客户端发送数据
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("I`m BIO send info!".getBytes());
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
    }
}

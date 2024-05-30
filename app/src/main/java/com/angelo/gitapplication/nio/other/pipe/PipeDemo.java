package com.angelo.gitapplication.nio.other.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * author: Angelo.Luo
 * date : 05/30/2024 4:40 PM
 * description:
 */
public class PipeDemo {
    public static void main(String[] args) throws IOException {

        Pipe pipe = Pipe.open();
        Thread01 thread01 = new Thread01(pipe);
        Thread02 thread02 = new Thread02(pipe);
        thread01.run();
        thread02.run();

    }
}

class Thread01 extends Thread {
    private Pipe pipe;

    public Thread01(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void run() {
        try {
            Pipe.SinkChannel channel = pipe.sink();
            channel.write(ByteBuffer.wrap("传输数据".getBytes()));
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Thread02 extends Thread {
    private Pipe pipe;

    public Thread02(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void run() {
        try {
            Pipe.SourceChannel channel = pipe.source();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            System.out.println(new String(buffer.array(), 0, buffer.limit()));
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


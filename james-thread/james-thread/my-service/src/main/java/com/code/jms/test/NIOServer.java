package com.code.jms.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class NIOServer {
    static ArrayList<SocketChannel> socketList = new ArrayList<>();
    static ByteBuffer byteBuffer = ByteBuffer.allocate(512);

    //NIO设计就是sun公司设计利用单线程来解决并发的问题
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        /*Selector s = Selector.open();
        s.select();*/
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 6379);
        serverSocket.bind(socketAddress);
        serverSocket.configureBlocking(false);//accept()变成非阻塞

        //单线程
        while (true) {
            for (SocketChannel socketChannel : socketList) {
                int read = socketChannel.read(byteBuffer);
                if (read > 0) {
                    System.out.println("read--------" + read);
                    byteBuffer.flip();

                    byte[] bs = new byte[read];
                    byteBuffer.get(bs);
                    String content = new String(bs);
                    System.out.println(content);
                    byteBuffer.flip();
                }
            }

            SocketChannel accept = serverSocket.accept();
            if (accept != null) {
                System.out.println("conn success-------");
                accept.configureBlocking(false);//去除阻塞
                socketList.add(accept);
                System.out.println("socketlist  Size ==" + socketList.size());
            }
        }
    }

    /*epoll是Linux内核为处理大批量文件描述符而作了改进的poll，
    是Linux下多路复用IO接口select/poll的增强版本，
    它能显著提高程序在大量并发连接中只有少量活跃的情况下的系统CPU利用率。
    另一点原因就是获取事件的时候，它无须遍历整个被侦听的描述符集，
    只要遍历那些被内核IO事件异步唤醒而加入Ready队列的描述符集合就行了。
    epoll除了提供select/poll那种IO事件的水平触发（Level Triggered）外，
    还提供了边缘触发（Edge Triggered），这就使得用户空间程序有可能缓存IO状态，
    减少epoll_wait/epoll_pwait的调用，提高应用程序效率。提高应用程序效率*/
}

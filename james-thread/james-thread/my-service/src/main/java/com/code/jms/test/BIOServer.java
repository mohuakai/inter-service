package com.code.jms.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//服务端
public class BIOServer {
    static byte[] bs = new byte[1024];
    static ArrayList<Socket> socketList = null;
    //BIO 和 NIO，nio用单线程完成并发 多用户
    public static void main(String[] args) throws IOException {
        //根本方法是解决阻塞问题
        ServerSocket serverSocket = new ServerSocket(6379);
        //serverSocket.setBlock(false);
        while (true) {
            if (socketList != null) {
                for (Socket socket : socketList) {
                    int read = socket.getInputStream().read(bs);//read方法也是阻塞的
                    if (read > 0) {
                        System.out.println(new String(bs));
                    }
                }
            }
            //但凡涉及到监听，肯定阻塞（放弃CPU）
            Socket clientSocket = serverSocket.accept();//阻塞,监听有没有客户端连接过来
            if(clientSocket != null) {
              //  serverSocket.setBlock(false);
                socketList.add(clientSocket);//将客户端访问的数据保存下来，否则会不存在
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
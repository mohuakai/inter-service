package com.code.jms.test;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",6379);
        /*socket.getOutputStream().write("abcd".getBytes());
        socket.close();*/

        //测试，监听键盘有没有输入
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        socket.getOutputStream().write(next.getBytes());
        socket.close();
    }
}

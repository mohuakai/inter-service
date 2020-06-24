package com.study.springboot_hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.LockSupport;

@SpringBootTest
class SpringbootHelloApplicationTests {

    static Thread t1 = null,t2 = null;
    static char[] aI = "1234567".toCharArray();
    static char[] aC = "ABCDEFG".toCharArray();
    @Test
    void contextLoads() {
    }

    public static void main(String[] args){
//        locksupport();
        sync();
    }

    public static void locksupport(){
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {
            for (char c : aI) {
                System.out.print(c);
                LockSupport.unpark(t2);//通知t2
                LockSupport.park();//阻塞
            }
        },"t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);

            }
        },"t2");

        t1.start();
        t2.start();
    }

    public static void sync(){
        final Object o = new Object();
        new Thread(() -> {
            synchronized (o){
                for (char c : aI) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();//必须，否则无法停止程序
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (o){
                for (char c : aC) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();//必须，否则无法停止程序
            }
        },"t2").start();

    }


}

package com.study.springboot_hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication 来标注一个主程序类 ， 说明这是一个Spring Boot应用
@SpringBootApplication
public class SpringbootHelloApplication {
    //将SpringBoot应用启动起来
    public static void main(String[] args) {
        SpringApplication.run(SpringbootHelloApplication.class, args);
    }

}

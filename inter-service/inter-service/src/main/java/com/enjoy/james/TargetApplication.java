package com.enjoy.james;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
* springboot程序启动入口，启动时包括了内置的tomcat容器
* @author 【享学课堂】 James老师QQ：1076258117 架构技术QQ群：684504192   
* @throws Exception
*/
@SpringBootApplication
public class TargetApplication  {

    public static void main(String[] args) {
        SpringApplication.run(TargetApplication.class, args);
    }

}

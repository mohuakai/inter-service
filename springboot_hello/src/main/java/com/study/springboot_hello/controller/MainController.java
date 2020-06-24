package com.study.springboot_hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 来甦
 * @Description TODO
 */
@RestController
public class MainController {
    @RequestMapping("/hello")
    public String toIndex() {
        return "hello Springboot";
    }
}

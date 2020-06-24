package cn.study.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 来甦
 * @Description TODO
 */
@Controller
public class MainController {
    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }
}

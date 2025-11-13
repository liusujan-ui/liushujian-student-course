package com.tencent.springbootfronttemplatethymeleafthree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 观自在
 * @date 2025-11-13 19:11
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(ModelMap modelMap) {
        modelMap.addAttribute("msg", "这是动态的数据");
        return "hello";
    }
}

package com.tencent.springbootfronttemplatethymeleafthree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 观自在
 * @date 2025-11-13 19:26
 */
@Controller
public class IfController {
    @RequestMapping("/if")
    public String hello(ModelMap modelMap) {
        modelMap.addAttribute("flag", "yes");
        modelMap.addAttribute("baidu","baidu.com");
        modelMap.addAttribute("taobao","taobao.com");
        modelMap.addAttribute("age",19);
        return "if";
    }
}

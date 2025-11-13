package com.tencent.springbootfronttemplatethymeleafthree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 观自在
 * @date 2025-11-13 19:14
 */
@Controller
public class TextController {
    @RequestMapping("/text")
    public String hello(ModelMap modelMap) {
        modelMap.addAttribute("id", "这是id");
        modelMap.addAttribute("value","这是value");
        modelMap.addAttribute("utext","这是utext里面的内容<b>这里加粗了</b>");
        modelMap.addAttribute("text","这是text里面的内容<b>这里加粗了</b>");
        return "text";
    }
}

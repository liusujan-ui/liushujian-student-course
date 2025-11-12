package com.tencent.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 观自在
 * @date 2025-11-12 20:14
 */
@Controller
public class WelcomeController {

    @RequestMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        request.setAttribute("name", "张三!");
        return "welcome";
    }
}

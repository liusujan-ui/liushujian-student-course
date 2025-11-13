package com.tencent.springbootfronttemplatethymeleafthree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 观自在
 * @date 2025-11-13 19:55
 */
@Controller
public class BaseController {

    @RequestMapping("/base")
    public String base(ModelMap model, HttpServletRequest request, HttpSession session) {
        model.put("a",123);
        model.put("b","abc");
        request.setAttribute("request","333");
        session.setAttribute("session","444");
        return "base";
    }
}

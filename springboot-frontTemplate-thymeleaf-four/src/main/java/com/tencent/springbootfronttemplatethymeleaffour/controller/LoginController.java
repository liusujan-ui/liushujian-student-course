package com.tencent.springbootfronttemplatethymeleaffour.controller;


import com.tencent.springbootfronttemplatethymeleaffour.entity.Product;
import com.tencent.springbootfronttemplatethymeleaffour.entity.Users;
import com.tencent.springbootfronttemplatethymeleaffour.repository.ProductRepository;
import com.tencent.springbootfronttemplatethymeleaffour.repository.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Resource
    private UsersRepository usersRepository;
    @Resource
    private ProductRepository productRepository;
    @RequestMapping("/user/login")
    public String login(ModelMap map, Users users, HttpSession session){
        map.addAttribute("username",users.getUsername());
        //进行用户名密码的校验
        if(usersRepository.login(users)!=0){
            //登录成功，把当前user对象注入到session中，进入拦截器里配置
            users.setId(usersRepository.findUidByUsername(users));
            session.setAttribute("userInfo",users);
            return "redirect:/user/list";
        }else{
            map.addAttribute("login_error","用户名密码错误");
            return "login.html";
        }

    }
    @RequestMapping("/user/list")
    public String show(ModelMap map,HttpSession session){
        List<Product> list = productRepository.findByUid(((Users) session.getAttribute("userInfo")).getId());
        map.addAttribute("list",list);
        return "list.html";
    }
}

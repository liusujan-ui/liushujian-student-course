package com.tencent.application.second.controller;

import com.tencent.application.second.entity.Users;
import com.tencent.application.second.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
   private UserRepository userRepository;

    // 如果使用POST模拟DELETE
    @PostMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable int userId) {
        try {
            log.info("开始删除用户，用户ID: {}", userId);
            userRepository.deleteUser(userId);
            log.info("用户删除成功，用户ID: {}", userId);
        } catch (Exception e) {
            log.error("删除用户失败，用户ID: {}, 错误信息: {}", userId, e.getMessage(), e);
            // 可以考虑抛出自定义异常或者返回错误信息
        }
        return "redirect:/user/list"; // 重定向回用户列表页
    }

    @RequestMapping("/user/list")
    public String findAll(ModelMap model) {
        List<Users> all = userRepository.findAll();
        model.addAttribute("users", all);
        return "list";
    }


    @RequestMapping("/user/login")
    public String login(String username, String password, ModelMap model, HttpSession session){
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)
        && username.equals("admin") && password.equals("123456")){
            session.setAttribute("username", username);
            return "redirect:/user/list";
        }else{
            model.addAttribute("login_error","用户名密码错误");
            return "login";
        }

    }



}

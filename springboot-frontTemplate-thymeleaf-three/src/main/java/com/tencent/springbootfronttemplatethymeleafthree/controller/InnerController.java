package com.tencent.springbootfronttemplatethymeleafthree.controller;

import com.tencent.springbootfronttemplatethymeleafthree.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author 观自在
 * @date 2025-11-13 19:11
 */
@Controller
public class InnerController {

    @RequestMapping("/inner")
    public String hello(ModelMap modelMap) {
        modelMap.put("name", "张三");
        modelMap.put("user", new User("李四",19,"123123"));
        modelMap.put("flag", true);
        ArrayList<User> users = new ArrayList<>();
        Collections.addAll(users,
                new User("王五",29,"123123"),
                new User("赵六",39,"22222")
                );
        modelMap.put("list", users);
        return "inner";
    }
}

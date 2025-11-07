package com.tencent.springbootspringdatarestfourth.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/hello")
    @ResponseBody
    public String test(){
        return "test";
    }
}

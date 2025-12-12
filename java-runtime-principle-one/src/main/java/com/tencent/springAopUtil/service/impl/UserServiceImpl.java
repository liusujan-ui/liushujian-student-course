package com.tencent.springAopUtil.service.impl;

import com.tencent.springAopUtil.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 观自在
 * @description
 * @date 2025-12-12 23:10
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    public String getUserName() {return "lll";}

    public UserServiceImpl(){
        System.out.println("UserServiceImpl constructor");
    }

    @PostConstruct  //bean初始化后的回调，会执行这个方法
    public void init(){
        System.out.println("UserServiceImpl init");
    }

}

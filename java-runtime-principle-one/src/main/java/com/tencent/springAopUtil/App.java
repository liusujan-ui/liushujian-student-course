package com.tencent.springAopUtil;

import com.tencent.springAopUtil.config.AppConfig;
import com.tencent.springAopUtil.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author 观自在
 * @description
 * @date 2025-12-12 23:07
 */
public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService bean = context.getBean(UserService.class);
        System.out.println(bean.getUserName());
    }
}

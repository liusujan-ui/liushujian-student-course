package com.tencent.springbootfronttemplatethymeleaffour.config;


import com.tencent.springbootfronttemplatethymeleaffour.interceptor.LoginHandlerIntercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 观自在
 * @Description 这个是配置类，默认是可以改变 WebMvcConfigurer的配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        System.out.println("hello");
        //转发
        registry.addViewController("/").setViewName("login.html");
//        registry.addViewController("/main.html").setViewName("/list.do");
        //重定向
//        registry.addRedirectViewController("/main.html","list.do");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerIntercept()).addPathPatterns("/**").excludePathPatterns("/","/login.html","/user/login","/css/**","js/**","img/**");
    }
}

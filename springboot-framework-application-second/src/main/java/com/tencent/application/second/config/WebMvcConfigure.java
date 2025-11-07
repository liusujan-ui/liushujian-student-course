package com.tencent.application.second.config;

import com.tencent.application.second.inteceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {


   /**
 * 配置视图控制器，将根路径 "/" 映射到 "login.html" 视图。
 * 当用户访问应用程序的根URL时，将会显示登录页面。
 *
 * @param registry 用于注册视图控制器的注册表
 */
@Override
public void addViewControllers(ViewControllerRegistry registry) {
    // 将根路径 "/" 映射到名为 "login.html" 的视图
    registry.addViewController("/").setViewName("login.html");
}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/login.html","/user/login","/css/**","/js/**","/img/**");
    }
}

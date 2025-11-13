package com.tencent.springbootfronttemplatethymeleaffour.interceptor;


import com.tencent.springbootfronttemplatethymeleaffour.entity.Users;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 来甦
 * @Description TODO
 */
public class LoginHandlerIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Users users = (Users) request.getSession().getAttribute("userInfo");
        if (users!=null){
            return true;
        }else{
            request.setAttribute("login_error","请先登录");
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
    }
}

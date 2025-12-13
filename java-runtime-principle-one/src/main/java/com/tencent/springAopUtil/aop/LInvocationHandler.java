package com.tencent.springAopUtil.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 观自在
 * @description
 * @date 2025-12-13 16:41
 */
public class LInvocationHandler implements InvocationHandler {
    Object target;
    public LInvocationHandler(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //前置操作
        System.out.println("l before");
        return method.invoke(target, args);
    }
}

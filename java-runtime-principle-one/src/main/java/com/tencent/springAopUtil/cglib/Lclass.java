package com.tencent.springAopUtil.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author 观自在
 * @description cglib动态代理，让代理类进行工作
 * @date 2025-12-12 23:27
 */

public class Lclass {
    public void test(){
        System.out.println("test");
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Lclass.class);
        enhancer.setCallback(new MethodInterceptor() {

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                Object result = methodProxy.invokeSuper(o, objects);
                System.out.println("after");
                return result;
            }
        });
        Lclass lclass = (Lclass) enhancer.create();
        lclass.test();
    }
}

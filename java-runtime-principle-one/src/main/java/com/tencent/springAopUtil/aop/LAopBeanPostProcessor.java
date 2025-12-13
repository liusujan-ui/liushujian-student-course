package com.tencent.springAopUtil.aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;

/**
 * @author 观自在
 * @description
 * @date 2025-12-13 16:38
 */
public class LAopBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("laopService")) {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            Object o = Proxy.newProxyInstance(LAopBeanPostProcessor.class.getClassLoader(), interfaces,
                    new LInvocationHandler(bean));
            return o;
        }
            return null;
    }
}

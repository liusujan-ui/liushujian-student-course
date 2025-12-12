package com.tencent.springIOCUtil.beanDefinitionTest;

import com.tencent.springAopUtil.service.UserService;
import com.tencent.springAopUtil.service.impl.UserServiceImpl;
import com.tencent.springIOCUtil.Lservice;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author 观自在
 * @description
 * @date 2025-12-12 07:56
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("so init ....");
        beanFactory.getBeanNamesIterator().forEachRemaining(System.out::println);
        AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition)beanFactory.getBeanDefinition("userServiceImpl");
        beanDefinition.setBeanClass(UserServiceImpl.class);
    }
}

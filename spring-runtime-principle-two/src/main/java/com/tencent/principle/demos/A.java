package com.tencent.principle.demos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 观自在
 * @description
 * @date 2025-12-20 00:11
 */

@Data
@Component
//@NoArgsConstructor
public class A   {
    private ApplicationContext applicationContext;

    private B b;

    public A() {
        System.out.println("A constructor is called");
    }


    @PostConstruct
    public void print() {
        System.out.println("A print is called");
    }


}

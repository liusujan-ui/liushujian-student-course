package com.tencent.principle.demos;

import lombok.AllArgsConstructor;
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
public class B   {
    private ApplicationContext applicationContext;
    private A a;

    public B() {
        this.a = a;
        System.out.println("B constructor is called");
    }

    @PostConstruct
    public void print() {
        System.out.println("B print is called");
    }


}

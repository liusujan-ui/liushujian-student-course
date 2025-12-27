package com.tencent.principle.demos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 观自在
 * @descriptionEchoServer
 * @date 2025-12-20 00:13
 */

public class Test {

//    @Autowired
//    private A a;
//
//    @Autowired
//    private B b;

    public static void main(String[] args) {
//        A a = new A();
//        B b = new B();
//
//        a.setB(b);
//        b.setA(a);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(context.getBean("a"));


    }
}

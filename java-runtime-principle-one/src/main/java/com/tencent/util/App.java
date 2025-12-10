package com.tencent.util;

import org.dom4j.DocumentException;

/**
 * @author 观自在
 * @description
 * @date 2025-12-10 22:59
 */
public class App {

    public static void main(String[] args) {
        //自己去写一个Application --帮助我们解析bean --map放在Application里面
        //spring -- xml 注解
        LclasspathXmlApplicationContext app = new LclasspathXmlApplicationContext("applicationContext.xml");
        try {
            Object bean = app.getBean("object");

            System.out.println(bean.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.tencent.principle;

import com.tencent.principle.demos.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootTest
class SpringRuntimePrincipleTwoApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        A a = (A) context.getBean("a");
        System.out.println(a);
    }

}

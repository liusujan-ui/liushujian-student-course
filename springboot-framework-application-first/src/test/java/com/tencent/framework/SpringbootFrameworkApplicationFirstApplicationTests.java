package com.tencent.framework;

import com.tencent.framework.demos.web.MysqlConfiguration;
import com.tencent.framework.demos.web.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootFrameworkApplicationFirstApplicationTests {

    @Autowired
    private Person person;

    @Autowired
    private MysqlConfiguration mysqlConfiguration;

    @Test
    void contextLoads() {
        System.out.println(person);

//        System.out.println(mysqlConfiguration.getUrl());
//
        mysqlConfiguration.func();
    }

}

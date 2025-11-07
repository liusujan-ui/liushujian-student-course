package com.tencent.mybatis;

import com.tencent.mybatis.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootFrameworkMybatisSixApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println(userDao.findAll());

        System.out.println("=============");

        System.out.println(userDao.findByName("王"));
    }


}

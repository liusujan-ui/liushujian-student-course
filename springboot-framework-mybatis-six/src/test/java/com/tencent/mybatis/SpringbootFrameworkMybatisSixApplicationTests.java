package com.tencent.mybatis;

import com.tencent.mybatis.dao.ProductDao;
import com.tencent.mybatis.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootFrameworkMybatisSixApplicationTests {

    @Resource
    private UserDao userDao;

    @Resource
    private ProductDao productDao;


    @Test
    void contextLoads() {
        System.out.println(userDao.findAll());

        System.out.println("=============");

        System.out.println(userDao.findByName("王"));

        System.out.println("===============");
        System.out.println(productDao.findAllByUid(1001));

        System.out.println("==============");
        System.out.println(userDao.findProductUserById(1001));
    }


}

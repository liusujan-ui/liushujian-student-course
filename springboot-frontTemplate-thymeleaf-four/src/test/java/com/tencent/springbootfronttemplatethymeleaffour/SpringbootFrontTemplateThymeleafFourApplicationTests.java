package com.tencent.springbootfronttemplatethymeleaffour;

import com.tencent.springbootfronttemplatethymeleaffour.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootFrontTemplateThymeleafFourApplicationTests {

    @Resource
    private UsersRepository usersRepository;
    @Test
    void contextLoads() {
        System.out.println(usersRepository.findUsersById(1001));
    }

}

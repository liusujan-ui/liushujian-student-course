package com.tencent.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.tencent.mybatis.dao")
@SpringBootApplication
public class SpringbootFrameworkMybatisSixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFrameworkMybatisSixApplication.class, args);
    }

}

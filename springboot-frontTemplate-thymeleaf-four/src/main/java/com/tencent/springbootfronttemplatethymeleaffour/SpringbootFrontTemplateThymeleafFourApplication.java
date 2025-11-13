package com.tencent.springbootfronttemplatethymeleaffour;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.tencent.springbootfronttemplatethymeleaffour.repository")
@SpringBootApplication
public class SpringbootFrontTemplateThymeleafFourApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFrontTemplateThymeleafFourApplication.class, args);
    }

}

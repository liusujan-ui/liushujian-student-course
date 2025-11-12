package com.tencent.jsp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tencent.jsp.repository")
@SpringBootApplication
public class SpringbootFrontTemplateJspOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFrontTemplateJspOneApplication.class, args);
    }

}

package com.tencent.page;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tencent.page.repository")
@SpringBootApplication
public class SpringbootFrameworkPageSevenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFrameworkPageSevenApplication.class, args);
    }

}

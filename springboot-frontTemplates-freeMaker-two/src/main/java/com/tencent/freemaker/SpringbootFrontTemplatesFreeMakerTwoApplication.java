package com.tencent.freemaker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tencent.freemaker.repository")
@SpringBootApplication
public class SpringbootFrontTemplatesFreeMakerTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFrontTemplatesFreeMakerTwoApplication.class, args);
    }

}

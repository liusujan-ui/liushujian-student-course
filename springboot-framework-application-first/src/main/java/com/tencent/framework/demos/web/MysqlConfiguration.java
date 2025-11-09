package com.tencent.framework.demos.web;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class MysqlConfiguration {
    private Logger logFactory=LoggerFactory.getLogger(MysqlConfiguration.class);

    @Value("${spring.datasource.url}")
    private  String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;



    public void func(){
        logFactory.info(url);
        return;
    }

}

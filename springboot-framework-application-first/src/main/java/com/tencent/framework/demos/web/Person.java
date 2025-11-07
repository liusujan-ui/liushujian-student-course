package com.tencent.framework.demos.web;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@ToString
@Component
@ConfigurationProperties(prefix = "person2")
@PropertySource("classpath:person.properties")
public class Person {
    private String name;
    private Integer age;
    private String sex;
    private boolean isMarried;
    private Friend friend;
    private String[] pets;
    private List<Friend> friends;
}

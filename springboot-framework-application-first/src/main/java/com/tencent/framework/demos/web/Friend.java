package com.tencent.framework.demos.web;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
public class Friend {
    @Value("${person.name}")
    private static String name;
    @Value("${person.sex}")
    private String sex;
    @Value("#{2*11}")
    private int age;





}

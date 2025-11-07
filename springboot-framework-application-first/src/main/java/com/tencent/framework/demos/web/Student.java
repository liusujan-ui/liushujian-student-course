package com.tencent.framework.demos.web;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String id;
    private String name;
    private int age;
}

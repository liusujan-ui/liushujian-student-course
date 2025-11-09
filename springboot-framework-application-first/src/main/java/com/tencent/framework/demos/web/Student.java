package com.tencent.framework.demos.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String id;
    private String name;
    private int age;
    private String username;


}

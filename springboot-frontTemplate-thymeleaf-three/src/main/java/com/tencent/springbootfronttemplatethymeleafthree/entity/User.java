package com.tencent.springbootfronttemplatethymeleafthree.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 观自在
 * @date 2025-11-13 19:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int age;
    private String pass;

}

package com.tencent.springbootfronttemplatethymeleaffour.entity;

import lombok.Data;

import java.util.List;

@Data
public class Users {
    private int id;
    private String username;
    private String password;
    private String name;
    private List<Product> products;
}

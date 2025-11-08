package com.tencent.mybatis.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private List<Product> products;
}

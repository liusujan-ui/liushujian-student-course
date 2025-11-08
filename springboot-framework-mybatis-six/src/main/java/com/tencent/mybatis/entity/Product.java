package com.tencent.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Product {
    private int id;
    private int uid;
    private String productName;
}

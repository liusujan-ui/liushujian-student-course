package com.tencent.springbootfronttemplatethymeleaffour.entity;

import lombok.Data;

/**
 * @Author 来苏
 * @Description TODO
 */
@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String pic;
    private int uid;
}

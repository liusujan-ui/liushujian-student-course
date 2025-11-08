package com.tencent.mybatis.dao;

import com.tencent.mybatis.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao {

    @Select("select id,uid,product_name from products where uid=#{uid}")
    List<Product> findAllByUid(int uid);
}

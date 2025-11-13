package com.tencent.springbootfronttemplatethymeleaffour.repository;


import com.tencent.springbootfronttemplatethymeleaffour.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductRepository {
    @Delete("delete from product where id=#{id}")
    void deleteById(int id);
    @Select("select * from product where id=#{id}")
    Product findById(int id);
    @Update("update product set name=#{product.name},description=#{product.description},price=#{product.price},pic=#{product.pic} where id=#{product.id}")
    void updateProduct(@Param("product") Product product);
    //这里是演示一对多中的多
    @Select("select * from product where uid=#{uid}")
    List<Product> findByUid(int uid);

    //新增产品的方法
    @Insert("insert into product(name,description,price,pic,uid) values(" +
            "#{product.name},#{product.description},#{product.price},#{product.pic},#{product.uid})")
    void addProduct(@Param("product") Product product);
}

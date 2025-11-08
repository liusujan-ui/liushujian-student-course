package com.tencent.mybatis.dao;

import com.tencent.mybatis.entity.Product;
import com.tencent.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserDao {

//    @Select("select * from users")
    List<User> findAll();

    User selectById(int id);


//    @Select("select id,username,password,name from users where name like #{name}")
    @Select("select id,username,password,name from users where name like '%${name}%'")
    User findByName(String name);

//  查询人员的product
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(property = "products",column = "id",many = @Many(select = "com.tencent.mybatis.dao.ProductDao.findAllByUid"))
    })
    User findProductUserById(int id);
}

package com.tencent.mybatis.dao;

import com.tencent.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

//    @Select("select * from users")
    List<User> findAll();

    User selectById(int id);


//    @Select("select id,username,password,name from users where name like #{name}")
    @Select("select id,username,password,name from users where name like '%${name}%'")
    User findByName(String name);
}

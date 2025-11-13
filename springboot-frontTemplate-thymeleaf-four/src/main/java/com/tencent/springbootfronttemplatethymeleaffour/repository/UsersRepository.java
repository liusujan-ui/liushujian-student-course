package com.tencent.springbootfronttemplatethymeleaffour.repository;


import com.tencent.springbootfronttemplatethymeleaffour.entity.Users;
import org.apache.ibatis.annotations.*;


public interface UsersRepository {
    @Select("select count(*) from users where username=#{users.username} and password=#{users.password}")
    int login(@Param("users") Users users);

    @Select("select id from users where username=#{users.username} and password=#{users.password}")
    int findUidByUsername(@Param("users") Users users);

    //演示一对多中的1，这里是根据id来查users对象，那么我们可以结合ProductRepository来一次性把对应的product也查出来
    //property指的是在Users实体类中一对多中的多的属性名，column代表的是把users表中的那个字段作为后面查询的条件，many指的是查询是一对多还是一对一
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(
                    property = "products",column = "id",many = @Many(select = "com.tencent.springbootfronttemplatethymeleaffour.repository.ProductRepository.findByUid")
            )
    })
    Users findUsersById(int id);
}

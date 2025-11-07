package com.tencent.application.second.service;

import com.tencent.application.second.entity.Users;
import com.tencent.application.second.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository {
    static List<Users> users = new ArrayList<>();
    static {
        Collections.addAll(users,
    new Users(1002,"lisi","123456","李四","男"),
    new Users(1003,"wangwu","123456","王五","女"),
    new Users(1004,"zhaoliu","123456","赵六","男"),
    new Users(1005,"sunqi","123456","孙七","女"),
    new Users(1006,"zhouba","123456","周八","男")
);

    }

    @Override
    public List<Users> findAll() {
        return users;
    }

    @Override
    public void deleteUser(int id) {
        int num=0;
        Iterator<Users> iterator = users.iterator();
        while (iterator.hasNext()) {
            Users user = iterator.next();
            if (user.getId()==id){
                iterator.remove();
                num++;
            }
        }
    }
}

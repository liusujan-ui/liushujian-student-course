package com.tencent.application.second.repository;

import com.tencent.application.second.entity.Users;

import java.util.List;

public interface UserRepository {
    List<Users> findAll();
    void deleteUser(int id);
}

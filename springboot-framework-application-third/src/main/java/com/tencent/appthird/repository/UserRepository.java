package com.tencent.appthird.repository;

import com.tencent.appthird.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

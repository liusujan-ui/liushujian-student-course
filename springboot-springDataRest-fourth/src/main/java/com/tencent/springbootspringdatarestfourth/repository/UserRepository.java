package com.tencent.springbootspringdatarestfourth.repository;

import com.tencent.springbootspringdatarestfourth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(@Param("username") String username);
}

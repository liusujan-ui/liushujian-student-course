package com.tencent.application.second.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users {
    private int id;
    private String username;
    private String password;
    private String name;
    private String userSex;


}

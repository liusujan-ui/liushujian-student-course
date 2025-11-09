package com.tencent.mybatisfifth.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentDO {
    private int id;
    private String name;
    private String sex;
}

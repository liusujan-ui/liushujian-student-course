package com.tencent.mybatisfifth.dao;


import com.tencent.mybatisfifth.entity.StudentDO;

import java.util.List;

public interface StudentMapper {

//    !-- 查询所有学生 -->
    StudentDO selectAll();

//            <!-- 根据ID查询学生 -->
    StudentDO selectById(int id);


    List<StudentDO> selectByName(String name);

// <!-- 插入学生 -->
    void insert(StudentDO student);

// <!-- 更新学生信息 -->
    void update(StudentDO student);

// <!-- 删除学生 -->
    void deleteById(int id);


}

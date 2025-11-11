package com.tencent.page.repository;

import com.tencent.page.entity.Emp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 观自在
 * @date 2025-11-11 22:31
 */
public interface EmpDao {
    @Select(value = "select empno,ename,job,sal,mgr,comm,deptno from emp")
    List<Emp> findAll1();
}

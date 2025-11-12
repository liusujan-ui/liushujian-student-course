package com.tencent.jsp.repository;

import com.tencent.jsp.entity.Emp;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 观自在
 * @date 2025-11-12 20:36
 */
@Repository
public interface EmpRepository {
    @Select("select empno,ename,sal,job,comm,mgr,deptno from emp")
    List<Emp> findAll();
}

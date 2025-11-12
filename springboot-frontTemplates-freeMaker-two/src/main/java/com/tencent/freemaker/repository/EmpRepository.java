package com.tencent.freemaker.repository;

import com.tencent.freemaker.entity.Emp;
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

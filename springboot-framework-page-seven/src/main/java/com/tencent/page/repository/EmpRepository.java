package com.tencent.page.repository;

import com.tencent.page.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 观自在
 * @date 2025-11-11 21:46
 */
public interface EmpRepository extends JpaRepository<Emp, Integer> {
}

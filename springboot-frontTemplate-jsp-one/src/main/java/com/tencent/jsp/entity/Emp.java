package com.tencent.jsp.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author 观自在
 * @date 2025-11-12 20:35
 */
@Data
@Builder
public class Emp {
    private int empno;
    private String ename;
    private Integer sal;
    private String job;
    private Integer comm;
    private Integer mgr;
    private Integer deptno;
}

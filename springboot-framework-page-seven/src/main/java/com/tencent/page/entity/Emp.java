package com.tencent.page.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "emp")
public class Emp {
    @Id
    @Column(name = "EMPNO", nullable = false)
    private Integer empno;

    @Column(name = "ENAME", length = 10)
    private String ename;

    @Column(name = "JOB", length = 9)
    private String job;

    @Column(name = "MGR")
    private Integer mgr;

    @Column(name = "SAL")
    private Integer sal;

    @Column(name = "COMM")
    private Integer comm;

    @Column(name = "DEPTNO")
    private Integer deptno;

}
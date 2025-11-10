package com.tencent.appthird.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "bid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bid;

    @Column(name = "bname", length = 128)
    private String bname;

    @Column(name = "bprice")
    private Double bprice;

    @Column(name = "pid", length = 32)
    private String pid;

}
package com.tencent.appthird.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GenericGenerator(name = "myuuid",strategy = "uuid")
    @GeneratedValue(generator = "myuuid")
    private String pid;

    @Column(name = "pname", length = 50,unique = true)
    private String pname;

    @Column(name = "psex", length = 10)
    private String psex;

    @Column(name = "page")
    private int page;

    @Column(name = "get_married")
    private Boolean getMarried;

}
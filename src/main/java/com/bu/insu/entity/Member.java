package com.bu.insu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/*
DB와 1:1로 만나는 클래스
 */
@Entity
@Table(name = "MEMBERS") //yaml에서 DB연결하고 엔티티 에서 DBeaver에 만들어 놓은 테이블을 연결함

public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime regDate;

}

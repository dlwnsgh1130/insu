package com.bu.insu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
DB와 1:1로 만나는 클래스
 */
@Entity
@Table(name = "MEMBERS") //yaml에서 DB연결하고 엔티티 에서 DBeaver에 만들어 놓은 테이블을 연결함
@NoArgsConstructor(access = AccessLevel.PROTECTED) //매개변수가 없는 기본 생성자
@AllArgsConstructor//매개변수가 있는 생성자
@Getter
@Builder

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSeq")
    @SequenceGenerator(name = "memberSeq", sequenceName = "MEMBER_SEQ", allocationSize = 1)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 10)
    @Builder.Default //Builder 패턴으로 객체를 만들 때 role 값을 안 넣으면 기본 USER 적용
    private Role role = Role.USER;

    @CreationTimestamp
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

}

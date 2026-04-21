package com.bu.insu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/*
DB와 1:1로 만나는 클래스
 */
@Entity
@Table(name = "MEMBERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 매개변수가 없는 기본 생성자
@AllArgsConstructor // 매개변수가 있는 생성자
@Getter
@Builder    // 빌더 패턴으로 객체 생성
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSeq")
    @SequenceGenerator(name = "memberSeq", sequenceName = "MEMBER_SEQ", allocationSize = 1)
    @Column(name = "MEMBER_ID")
    private Long id;


    // unique 중복 여부, nullable = false null 불가
    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;


    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;


    @Column(name = "NAME" , nullable = false, length = 50)
    private String name;


    @Enumerated(EnumType.STRING)    // enum을 DB에 저장하는 방식 지정
    @Column(name = "ROLE", nullable = false, length = 10)
    @Builder.Default    // Builder 패턴으로 객체를 만들 때 role 값을 안 넣으면 기본 USER 적용
    private Role role = Role.USER;


    @CreationTimestamp  // insert 시점에 현재 시간 자동 입력
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;


    // 회원 수정 메서드
    public void updateInfo(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

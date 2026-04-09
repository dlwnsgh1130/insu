package com.bu.insu.repository;


import com.bu.insu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
JPA API
SQL 작성없이 CURD, page, 정렬 등 구현 가능
JpaRepository<엔티티타입, PK 타입> 상속
*/
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Optional 결과가 있을 수도 있고 없을 수도 있으니 null 대신 감싸서 반환
    // 매서드이름 규칙: findBy+필드명 -> JPA가 자동으로 쿼리문 작성
    // SELECT * FROM MEMBERS WHERE EMAIL = ?
    Optional<Member> findByEmail(String email);
}

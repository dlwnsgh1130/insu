package com.bu.insu.dto;


/*
서버에서 클라이언트로 전달되는 응답 데이터 DTO
Entity가 직접 반환하지 않고 응답에 필요한 필드만 전달


Record
Java 16+ 불변 데이터 클래스
모든 필드는 final을 가짐
Getter, NoArgsConstructor, Builder 등 자동 생성
*/


import com.bu.insu.entity.Member;
import com.bu.insu.entity.Role;

import java.time.LocalDateTime;


public record MemberResponse(
        Long id,
        String email,
        String name,
        Role role,
        LocalDateTime regDate
) {
    // Entity -> DTO 변환
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getRegDate()
        );
    }
}




/*
   public MemberResponse(Long id, String email, String name, Role role, LocalDateTime regDate) {
       this.id = id;
       this.email = email;
       this.name = name;
       this.role = role;
       this.regDate = regDate;
   }


   MemberResponse mr = new MemberResponse();
   mr.setEmail();
   mr.setEmail();
   mr.setEmail();
   mr.setEmail();
*/

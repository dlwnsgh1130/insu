package com.bu.insu.dto;

import com.bu.insu.entity.Member;
import com.bu.insu.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {


    private Long id;
    private String email;
    private String name;
    // 비밀번호는 포함하지 않음
    private Role role;
    private LocalDateTime regDate;


    // 빌더 패턴
    // Entity -> DTO 변환
    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .regDate(member.getRegDate())
                .build();
    }
}

package com.bu.insu.dto;

import com.bu.insu.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequest {

    @NotBlank()
    private String email;
    private String password;
    private String name;

    // DTO -> Entity 변환
    public Member toEntity(){

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

    }

}

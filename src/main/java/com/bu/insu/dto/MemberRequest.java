package com.bu.insu.dto;


import com.bu.insu.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


// 클라이언트 -> 서버로 전달되는 요청 데이터
public record MemberRequest(
        @NotBlank(message = "이메일은 필수값입니다.") // null, "", " " 허용하지 않음
        @Email(message = "이메일 형식이 아닙니다.") // 이메일 형식 검증
        @Size(max = 100, message = "이메일은 100자 이하여야 합니다.") // 글자수 제한
        String email,


        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 사이어야 합니다.")
        String password,


        @NotBlank(message = "이름은 필수값입니다.")
        @Size(min = 2, max = 50, message = "이름은 2~50자 사이어야 합니다.")
        String name
) {


    // DTO -> Entity 변환
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}

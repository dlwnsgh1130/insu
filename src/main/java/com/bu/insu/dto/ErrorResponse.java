package com.bu.insu.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/*
오류 응답 형식(DTO) 정의
오류 발생시 클라이언트에게 일괄적인 형식으로 응답
 */
@Builder
@Getter

public class ErrorResponse {

    private int status;
    private String message;
    private List<String> errors;

    @Builder.Default
    private LocalDateTime timestamp =LocalDateTime.now();

}
/* JSON
{
   "status" : 404,
   "message" : "회원을 찾을 수 없습니다.",
   "errors" : null,
   "timestamp" : "2026-04-13Txxxx"
}
*/


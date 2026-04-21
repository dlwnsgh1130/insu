package com.bu.insu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

// 모든 API를 감싸는 공통 Wrapper -> 성공이든 실패든 같은 구조로 응답
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON 응답에서 제외
public class ApiResponse<T> {
    private boolean success;    // 성공 여부
    private T data;             // 성공시 응답 데이터(실패 시 null -> JSON에서 제외)
    private String message;     // 에러 메시지(실패시 사용)
    private int status;         // HTTP 상태 코드(실패시 사용)
    private List<String> errors;// 상세 오류 목록(Validation 실패시)


    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();


    // 성공 응답 -> ApiResponse.ok(data)
    // <T> 아무 타입이나 정해서 쓸 때
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }


    // 실패 응답 -> 상태코드, 오류 메시지
    // ? 와일드 카드: 아무 타입이나 다 된다. 데이터 타입에 관심없음
    public static ApiResponse<?> error(int status, String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .status(status)
                .build();
    }


    // 실패 응답 -> 상태코드, 오류 메시지, 상세 오류 목록
    public static ApiResponse<?> error(int status, String message, List<String> errors) {
        return ApiResponse.builder()
                .success(false)
                .status(status)
                .message(message)
                .errors(errors)
                .build();
    }


}

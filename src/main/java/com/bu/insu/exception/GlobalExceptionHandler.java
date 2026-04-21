package com.bu.insu.exception;

import com.bu.insu.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/*
전역 예외 처리 클래스
@RestControllerAdvice 전역에서 던진 예외를 낚아챔
*/
@RestControllerAdvice
public class GlobalExceptionHandler {


    // 모든 비즈니스 예외를 하나의 핸들러로 통합
    @ExceptionHandler(BusinessBaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessBaseException e) {
        return ResponseEntity
                .status(e.getStatus())  // 예외가 가진 상태 코드 사용
                .body(ApiResponse.error(e.getStatus().value(), e.getMessage()));
    }


    // @Valid 검증이 실패하면 자동으로 던지는 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField()  + ": " + err.getDefaultMessage())
                .toList();


        return ResponseEntity.badRequest().body(ApiResponse.error(400, "입력값 검증에 실패함", errors));
    }


   /* 회원을 찾을 수 없을 때 -> 404
   @ExceptionHandler(value = MemberNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleMemberNotFound(MemberNotFoundException e) {
       ErrorResponse response = ErrorResponse.builder()
               .status(HttpStatus.NOT_FOUND.value())
               .message(e.getMessage())
               .build();
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
   }


   // 이메일 중복시 -> 409
   @ExceptionHandler(DuplicationEmailException.class)
   public ResponseEntity<ErrorResponse> handleDuplicationEmail(DuplicationEmailException e) {
       ErrorResponse response = ErrorResponse.builder()
               .status(HttpStatus.CONFLICT.value())
               .message(e.getMessage())
               .build();


       return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
   }
   */
}


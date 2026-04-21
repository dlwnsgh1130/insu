package com.bu.insu.exception;

/*
모든 비즈니스 예외의 최상의 부모 클래스
각 하위 예외가 HTTP 상태를 가지고 있어서
GlobalExceptionHandler에 예외마다 핸들러를 만들지 않기 위해
*/


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessBaseException extends RuntimeException {


    private final HttpStatus status;


    public BusinessBaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;// HTTP 상태 코드 저장
    }




}

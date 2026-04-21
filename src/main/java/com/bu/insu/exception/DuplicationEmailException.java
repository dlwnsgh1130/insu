package com.bu.insu.exception;

import org.springframework.http.HttpStatus;

// 사용자 정의 예외처리
// 이메일 중복 확인
public class DuplicationEmailException extends BusinessBaseException {


    public DuplicationEmailException(String email) {
        super("이미 등록된 이메일입니다. " + email, HttpStatus.CONFLICT);
    }
}

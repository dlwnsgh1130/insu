package com.bu.insu.exception;

import org.springframework.http.HttpStatus;

/*
사용자 정의 예외처리 생성
- 회원을 찾을 수 없을 때 발생하는 예외
*/
public class MemberNotFoundException extends BusinessBaseException {
    public MemberNotFoundException(Long id) {
        super("회원을 찾을 수 없습니다. id: " + id,  HttpStatus.NOT_FOUND);
    }
}

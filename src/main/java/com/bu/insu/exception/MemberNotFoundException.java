package com.bu.insu.exception;
/*
사용자 정의 예외처리 생성
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("회원을 찾을 수 없습니다. " + id + " ");
    }


}

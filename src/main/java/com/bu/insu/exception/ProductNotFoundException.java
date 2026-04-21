package com.bu.insu.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessBaseException{


    public ProductNotFoundException(Long id) {
        super("상품을 찾을 수 없습니다. id: " + id, HttpStatus.NOT_FOUND);
    }
}

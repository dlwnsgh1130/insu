package com.bu.insu.repository;

import com.bu.insu.dto.ProductSearchRequest;
import com.bu.insu.entity.Product;

import java.util.List;

// QueryDSL로 구현할 메서드 정의
public interface ProductRepositoryCustom {

    // 동적 조건 검색: 입력된 조건만 WHERE에 포함(null인 조건은 무시)
    List<Product> search(ProductSearchRequest request);
}

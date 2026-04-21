package com.bu.insu.dto;


import java.math.BigDecimal;

// 클라이언트가 서버로 보내는 검색 조건 양식
public record ProductSearchRequest(
        String category,        // 카테고리
        String company,         // 보험사
        String keyword,         // 상품명 키워드
        BigDecimal minPremium,  // 최소 보험료
        BigDecimal maxPremium,  // 최대 보험료
        Double minRating        // 최소 평점
) {

    public ProductSearchRequest {
        category = blankToNull(category);
        company = blankToNull(company);
        keyword = blankToNull(keyword);
    }


    private String blankToNull(String s) {
        return (s != null && !s.isBlank()) ? s : null;
    }
}






/*
모든 필드가 null을 허용 - null이면 해당 조건을 무시
*/

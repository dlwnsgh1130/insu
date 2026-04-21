package com.bu.insu.dto;

import com.bu.insu.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

// 클라이언트 -> 서버
public record ProductRequest(
        @NotBlank(message = "상품명은 필수값입니다.")
        @Size(max = 200, message = "상품명은 200자이하여야 합니다.")
        String name,


        @NotBlank(message = "카테고리는 필수값입니다.")
        String category,


        @NotBlank(message = "회사명은 필수값입니다.")
        String company,


        @NotNull(message = "월 보험료는 필수값입니다.") // ""
        @Positive(message = "월보험료는 0보다 커야 합니다.")// 반드시 0보다 커야 함을 강제
        BigDecimal premium,


        BigDecimal coverage,
        Integer minAge,
        Integer maxAge,
        Integer period,
        Double rating,
        LocalDate launchDate,


        @Size(max = 4000, message = "상품 설명은 4000자 이하여야 합니다.")
        String description


) {
    // DTO -> Entity
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .category(category)
                .company(company)
                .premium(premium)
                .coverage(coverage)
                .minAge(minAge)
                .maxAge(maxAge)
                .period(period)
                .rating(rating)
                .launchDate(launchDate != null ? launchDate : LocalDate.now())
                .description(description)
                .build();
    }
}

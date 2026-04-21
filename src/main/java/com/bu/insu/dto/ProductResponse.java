package com.bu.insu.dto;

import com.bu.insu.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        String category,
        String company,
        BigDecimal premium,
        BigDecimal coverage,
        Integer minAge,
        Integer maxAge,
        Integer period,
        Double rating,
        LocalDate launchDate,
        String active,
        String description,
        LocalDate regDate
) {
    // Entity -> DTO
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getCompany(),
                product.getPremium(),
                product.getCoverage(),
                product.getMinAge(),
                product.getMaxAge(),
                product.getPeriod(),
                product.getRating(),
                product.getLaunchDate(),
                product.getActive(),
                product.getDescription(),
                product.getRegDate()
        );
    }
}

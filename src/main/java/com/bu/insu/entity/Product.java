package com.bu.insu.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "PRODUCTS", indexes = {
        // 단일 컬럼 인덱스
        @Index(name = "IDX_PRODUCTS_COMPANY", columnList = "COMPANY"),
        @Index(name = "IDX_PRODUCTS_CATEGORY", columnList = "CATEGORY"),
        @Index(name = "IDX_PRODUCTS_PREMIUM", columnList = "PREMIUM"),
        @Index(name = "IDX_PRODUCTS_RATING", columnList = "RATING DESC"),


        // 복합 컬럼 인덱스
        // WHERE CATEGORY = ? AND ACTIVE = ?;
        @Index(name = "IDX_PRODUCTS_CATEGORY_ACTIVE", columnList = "CATEGORY, ACTIVE"),
        @Index(name = "IDX_PRODUCTS_COMPANY_ACTIVE", columnList = "COMPANY, ACTIVE")
})
@EntityListeners(AuditingEntityListener.class)// Auditing 리스너 등록. 이 Entity 감시
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeq")
    @SequenceGenerator(name = "productSeq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "PRODUCT_ID")
    private Long id;


    @Comment("상품명")
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;


    @Column(name = "CATEGORY", nullable = false, length = 50)
    private String category;


    @Column(name = "COMPANY", nullable = false, length = 100)
    private String company;


    @Comment("월 보험료")
    @Column(name = "PREMIUM", nullable = false, precision = 12, scale = 0)
    private BigDecimal premium;


    @Column(name = "COVERAGE", precision = 15, scale = 0)
    private BigDecimal coverage;


    @Column(name = "MIN_AGE")
    private Integer minAge;


    @Column(name = "MAX_AGE")
    private Integer maxAge;


    @Column(name = "PERIOD")
    private Integer period;


    @Column(name = "RATING")
    private Double rating;


    @Column(name = "LAUNCH_DATE")
    private LocalDate launchDate;


    @Column(name = "ACTIVE", length = 1)
    @Builder.Default
    private String active = "Y";


    @Lob
    @Column(name = "DESCRIPTION")
    private String description;


    // DB에 전송되기 직전에 시간을 엔티티 필드에 주입
    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDate regDate;


    // 상품 수정 메서드
    public void updateInfo(String name, String category, String company, BigDecimal premium,
                           BigDecimal coverage, Integer minAge, Integer maxAge,
                           Integer period, Double rating, String description) {


        this.name = name;// 이 필드에 파라미터로 넣은 값을 대입 해 업데이트
        this.category = category;
        this.company = company;
        this.premium = premium;
        this.coverage = coverage;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.period = period;
        this.rating = rating;
        this.description = description;
    }


    // 얕은 삭제 : 상품 비활성화
    public void stopSelling() {
        this.active = "N";
    }






}

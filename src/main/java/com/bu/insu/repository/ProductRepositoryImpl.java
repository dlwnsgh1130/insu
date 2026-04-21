package com.bu.insu.repository;


import com.bu.insu.dto.ProductSearchRequest;
import com.bu.insu.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.bu.insu.entity.QProduct.product;


/* ProductRepositoryCustom 구현체
  실제 QueryDSL 쿼리 로직


  - QProduct: Product entity를 분석해 자동으로 만들어 준 쿼리 전용 클래스
  - JPAQueryFactory: JPQL -> SQL로 변환. EntityManager를 감싸서 메서드 체인으로 쿼리를 짜게 해 주는 wrapper
  - BooleanExpression: QueryDSL에서 true/false로 반환하는 조건식 데이터 타입
*/


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    @Override
    public List<Product> search(ProductSearchRequest request) {
        return queryFactory
                .selectFrom(product) // == select * from products;
                .where(
                        product.active.eq("Y"),
                        eqCategory(request.category()),
                        eqCompany(request.company()),
                        containsKeyword(request.keyword()),
                        goePremium(request.minPremium()),
                        loePremium(request.maxPremium()),
                        goeRating(request.minRating())
                )
                .orderBy(product.rating.desc())
                .fetch(); // 데이터베이스로 전송
    }




    // 카테고리
    private BooleanExpression eqCategory(String category) {
        // 사용자가 카테고리를 입력했으면 조건식을 반환하고 입력하지 않았으면 null 반환
        return category != null ? product.category.eq(category) : null;
    }


    // 보험사
    private BooleanExpression eqCompany(String company) {
        return company != null ? product.company.eq(company) : null;
    }


    // 키워드
    private BooleanExpression containsKeyword(String keyword) {
        return keyword != null ? product.name.containsIgnoreCase(keyword) : null;
    }


    // 최소 보험료
    private BooleanExpression goePremium(BigDecimal minPremium) {
        // .goe() >=
        return minPremium != null ? product.premium.goe(minPremium) : null;
    }


    // 최대 보험료
    private BooleanExpression loePremium(BigDecimal maxPremium) {
        // .loe() <=
        return maxPremium != null ? product.premium.loe(maxPremium) : null;
    }


    // 최소 평점
    private BooleanExpression goeRating(Double minRating) {
        return minRating != null ? product.rating.goe(minRating) : null;
    }




}
/*
조회
queryFactory.selectFrom(product) 전체 조회
queryFactory.select(product.name).from(product) 일부 컬럼만 조회
queryFactory.select(product.name, product.company).from(product) 여러 컬럼 조회


조건
.where(product.category.eq("화재보험"))  ==
.where(product.premium.gt(50000)) >
.where(product.premium.lt(50000)) <
.where(product.name.startsWith("삼성")) Like '삼성%'
.where(product.name.contains("삼성")) Like '%삼성%' 인덱스 검색이 안됨


정렬
.fetch() 결과 전체
.fetchOne() 단일 결과. 없으면 null
.fetchCount() 갯수
.fetchFirst() 첫번째 하나만
*/

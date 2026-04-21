package com.bu.insu.repository;


import com.bu.insu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


    // 카테고리별 활성 상품 조회
    // select * from PRODUCTS where CATEGORY = '생명보험' and ACTIVE ='Y';
    List<Product> findByCategoryAndActive(String category, String active);


    // 상품명 키워드 검색 Like '%키워드%'
    // select * from PRODUCTS where NAME Like '%?%';
    List<Product> findByNameContaining(String keyword);


}
/*
Query Method
findBy(== select) + Category           + And                             [ + Active ]
..By              + 속성(엔티티의 필드명) + 키워드(Or, Between, Containing..)[ + 속성(엔티티의 필드명) ]


Containing 문자열이 포함되었는지 검사하는 키워드 == Like '%키워드%'
*/

package com.bu.insu.service;


import com.bu.insu.dto.ProductSearchRequest;
import com.bu.insu.entity.Product;
import com.bu.insu.exception.ProductNotFoundException;
import com.bu.insu.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {


    private final ProductRepository productRepository;


    // 전체 상품 조회
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    // 상품 등록
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }


    // id 개별 조회
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    // 카테고리별 활성 상품 조회
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategoryAndActive(category, "Y");
    }


    // 상품 키워드 검색
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }


    // QueryDSL 동적 검색
    public List<Product> search(ProductSearchRequest request) {
        return productRepository.search(request);
    }


    // 상품 수정
    @Transactional
    public Product update(Long id,  String name, String category, String company,
                          BigDecimal premium, BigDecimal coverage, Integer minAge,
                          Integer maxAge, Integer period, Double rating, String description) {


        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));


        product.updateInfo(name, category, company, premium, coverage,
                minAge, maxAge, period, rating, description);
        return product;
    }


    // 얕은 삭제
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));


        product.stopSelling();
    }


}

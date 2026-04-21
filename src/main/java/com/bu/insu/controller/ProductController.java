package com.bu.insu.controller;



import com.bu.insu.dto.ApiResponse;
import com.bu.insu.dto.ProductRequest;
import com.bu.insu.dto.ProductResponse;
import com.bu.insu.dto.ProductSearchRequest;
import com.bu.insu.entity.Product;
import com.bu.insu.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;


    // 전체 상품 조회 GET /api/products
    @GetMapping
    public ApiResponse<List<ProductResponse>> findAll() {
        List<ProductResponse> products = productService.findAll().stream()
                // .map(product -> ProductResponse.from(product))
                .map(ProductResponse::from)
                .toList();


        return ApiResponse.ok(products);
    }


    // 상품 등록 POST /api/products
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(
            @Valid @RequestBody ProductRequest request) {
        Product saved = productService.save(request.toEntity());// DTO -> Entity 변환 -> DB 저장
        ProductResponse response = ProductResponse.from(saved);// Entity -> DTO
        return ResponseEntity
                .created(URI.create("/api/products/" + saved.getId())) // 상태코드 201 + Location
                .body(ApiResponse.ok(response));// 공통 응답 스타일
    }


    // ID 개별 조회
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ApiResponse.ok(ProductResponse.from(product));
    }


    // 카테고리별 활성 상품 조회
    // GET /api/products/category/생명보험
    @GetMapping("/category/{category}")
    public ApiResponse<List<ProductResponse>> findByCategory(@PathVariable String category) {


        List<ProductResponse> products = productService.findByCategory(category).stream()
                .map(ProductResponse::from)
                .toList();


        return ApiResponse.ok(products);
    }


    // 상품명 키워드 검색
    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(@RequestParam String keyword) {
        List<ProductResponse> products = productService.searchByName(keyword).stream()
                .map(ProductResponse::from) // Entity -> DTO
                .toList();
        return ApiResponse.ok(products);
    }


    // QueryDSL 동적 검색 - 입력한 조건만 검색, 나머지는 무시
    @GetMapping("/condition")
    public ApiResponse<List<ProductResponse>> searchByCondition(ProductSearchRequest request) {
        List<ProductResponse> produts = productService.search(request).stream()
                .map(ProductResponse::from)
                .toList();
        return ApiResponse.ok(produts);// DTO -> JSON
    }




    // 상품 수정
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable Long id, @Valid @RequestBody ProductRequest requestDto) {
        Product updated = productService.update(id,
                requestDto.name(), requestDto.category(), requestDto.company(),
                requestDto.premium(), requestDto.coverage(), requestDto.minAge(),
                requestDto.maxAge(), requestDto.period(), requestDto.rating(),
                requestDto.description()
        );// Entity


        return ApiResponse.ok(ProductResponse.from(updated));// 클라이언트 쪽으로 넘길 땐 DTO -> JSON변환
    }


    // 상품 삭제 active = "N"
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}



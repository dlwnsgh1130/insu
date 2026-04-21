package com.bu.insu.controller;


import com.bu.insu.dto.ProductSearchRequest;
import com.bu.insu.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// 화면을 반환하는 컨트롤러
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductViewController {


    private final ProductService productService;


    // 조회
    @GetMapping
    public String list(ProductSearchRequest searchRequest, Model model) {


        return "product/list"; // templates/product/list.html
    }
}
/*
Model : 화면(View)으로 데이터를 전달하는 상자. Map<String, object>


*/

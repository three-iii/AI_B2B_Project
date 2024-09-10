package com.three_iii.company.controller;

import com.three_iii.company.application.ProductService;
import com.three_iii.company.application.dto.ProductCreateRequest;
import com.three_iii.company.application.dto.ProductResponse;
import com.three_iii.company.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "Product CRUD")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 생성", description = "상품을 생성한다.")
    public Response<ProductResponse> createCompany(
        @RequestBody @Valid ProductCreateRequest requestDto) {
        return Response.success(productService.createProduct(requestDto));
    }

}

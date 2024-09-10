package com.three_iii.company.controller;

import com.three_iii.company.application.ProductService;
import com.three_iii.company.application.dto.ProductCreateRequest;
import com.three_iii.company.application.dto.ProductResponse;
import com.three_iii.company.application.dto.ProductUpdateRequest;
import com.three_iii.company.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Response<ProductResponse> createProduct(
        @RequestBody @Valid ProductCreateRequest requestDto) {
        return Response.success(productService.createProduct(requestDto));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 단건 조회", description = "상품을 단건 조회한다.")
    public Response<ProductResponse> findProduct(@PathVariable UUID productId) {
        return Response.success(productService.findProduct(productId));
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품을 수정한다.")
    public Response<ProductResponse> updateProduct(@PathVariable UUID productId,
        @RequestBody ProductUpdateRequest requestDto) {
        return Response.success(productService.updateProduct(requestDto, productId));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제한다.")
    public Response<?> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return Response.success("해당 상품이 삭제되었습니다.");
    }
}

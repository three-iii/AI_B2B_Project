package com.three_iii.company.presentation;

import com.three_iii.company.application.dtos.product.ProductResponse;
import com.three_iii.company.application.service.ProductService;
import com.three_iii.company.domain.UserPrincipal;
import com.three_iii.company.exception.Response;
import com.three_iii.company.presentation.dtos.ProductCreateRequest;
import com.three_iii.company.presentation.dtos.ProductUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "Product CRUD")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 생성", description = "상품을 생성한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<ProductResponse> createProduct(
        @RequestBody @Valid ProductCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(productService.createProduct(request.toDTO(), userPrincipal.getId(),
            userPrincipal.getRole()));
    }

    @PostMapping("/ai")
    @Operation(summary = "AI를 활용한 상품 설명 생성", description = "AI를 활용하여 상품 설명을 생성한다. description에 AI에게 요청 할 질문을 넣어주세요")
    public Response<ProductResponse> createProductByAi(
        @RequestBody @Valid ProductCreateRequest request) {
        return Response.success(productService.createProductByAi(request.toDTO()));
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 단건 조회", description = "상품을 단건 조회한다.")
    public Response<ProductResponse> findProduct(@PathVariable UUID productId) {
        return Response.success(productService.findProduct(productId));
    }

    @GetMapping
    @Operation(summary = "상품 전체 조회", description = "상품을 전체 조회한다.")
    public Response<Page<ProductResponse>> findAllProduct(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) UUID hubId,
        @RequestParam(required = false) UUID companyId,
        Pageable pageable) {
        return Response.success(productService.findAllProduct(keyword, hubId, companyId, pageable));
    }

    @PutMapping("/{productId}/stock")
    @Operation(summary = "상품 재고 업데이트")
    public Response<Void> updateProductStock(@PathVariable UUID productId,
        @RequestParam int quantity) {
        productService.updateProductStock(productId, quantity);
        return Response.success(null);
    }

    @PutMapping("/restore-stock/{productId}")
    public Response<Void> restoreStock(@PathVariable UUID productId, @RequestParam int quantity) {
        productService.restoreStock(productId, quantity);
        return Response.success(null);
    }


    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품을 수정한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<ProductResponse> updateProduct(
        @PathVariable UUID productId,
        @RequestBody ProductUpdateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return Response.success(
            productService.updateProduct(request.toDTO(), productId, userPrincipal.getId(),
                userPrincipal.getRole()));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<?> deleteProduct(
        @PathVariable UUID productId,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        productService.deleteProduct(productId, userPrincipal.getId(), userPrincipal.getRole(),
            userPrincipal.getUsername());
        return Response.success("해당 상품이 삭제되었습니다.");
    }
}

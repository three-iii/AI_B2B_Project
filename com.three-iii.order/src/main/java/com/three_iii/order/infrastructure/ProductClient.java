package com.three_iii.order.infrastructure;

import com.three_iii.order.application.dto.CompanyDto;
import com.three_iii.order.application.dto.ProductDto;
import com.three_iii.order.config.FeignConfig;
import com.three_iii.order.exception.Response;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company", url = "localhost:19091", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/api/products")
    Response<Page<ProductDto>> findAllProduct();

    @PutMapping("/api/products/{productId}/stock")
    void updateProductStock(@PathVariable UUID productId, @RequestParam int quantity);

    @PutMapping("/api/products/restore-stock/{productId}")
    void restoreStock(@PathVariable("productId") UUID productId,
        @RequestParam int quantity);

    @GetMapping("/api/companies")
    Response<Page<CompanyDto>> getCompanyById();
}

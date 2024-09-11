package com.three_iii.company.domain.repository;

import com.three_iii.company.application.dtos.product.ProductResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<ProductResponse> searchProduct(String keyword, UUID hubId, UUID companyId,
        Pageable pageable);

}

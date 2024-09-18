package com.three_iii.order.application;

import com.three_iii.order.application.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductDto> findAllProduct();
}

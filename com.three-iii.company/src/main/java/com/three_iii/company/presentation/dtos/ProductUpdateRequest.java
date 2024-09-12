package com.three_iii.company.presentation.dtos;

import com.three_iii.company.application.dtos.product.ProductUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    String name;
    Integer quantity;

    public ProductUpdateDto toDTO() {
        return ProductUpdateDto.create(this.name, this.quantity);
    }
}

package com.three_iii.company.application.dtos.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProductUpdateDto {

    private String name;
    private Integer quantity;

    public static ProductUpdateDto create(String name, Integer quantity) {
        return ProductUpdateDto.builder()
            .name(name)
            .quantity(quantity)
            .build();
    }
}

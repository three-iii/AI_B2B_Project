package com.three_iii.company.application.dtos.product;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductDto {

    private UUID companyId;
    private UUID hubId;
    private String name;
    private String description;
    private int quantity;

    public static ProductDto create(UUID companyId, UUID hubId, String name, String description,
        int quantity) {
        return ProductDto.builder()
            .companyId(companyId)
            .hubId(hubId)
            .name(name)
            .description(description)
            .quantity(quantity)
            .build();
    }
}

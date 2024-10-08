package com.three_iii.company.application.dtos.product;

import com.three_iii.company.domain.Product;
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
public class ProductResponse {

    private UUID id;
    private String name;
    private String description;
    private int quantity;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .quantity(product.getQuantity())
            .build();
    }
}

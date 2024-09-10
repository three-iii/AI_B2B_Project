package com.three_iii.company.application.dto;

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
    private CompanyResponse company;
    private UUID hubId;
    private String name;
    private int quantity;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .company(CompanyResponse.fromEntity(product.getCompany()))
            .hubId(product.getHubId())
            .name(product.getName())
            .quantity(product.getQuantity())
            .build();
    }
}

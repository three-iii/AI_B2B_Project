package com.three_iii.company.application.dtos.company;

import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.CompanyTypeEnum;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private UUID hubId;
    private UUID id;
    private Long userId;
    private String name;
    private CompanyTypeEnum type;
    private String address;
//    private List<ProductResponse> productList;

    public static CompanyResponse fromEntity(Company company) {
        return CompanyResponse.builder()
            .hubId(company.getHubId())
            .userId(company.getUserId())
            .id(company.getId())
            .name(company.getName())
            .type(company.getType())
            .address(company.getAddress())
//            .productList(company.getProductList().stream()
//                .map(ProductResponse::fromEntity).toList())
            .build();
    }
}

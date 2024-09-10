package com.three_iii.company.application.dto;

import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.CompanyTypeEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateResponse {

    private UUID id;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyCreateResponse fromEntity(Company company) {
        return CompanyCreateResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .type(company.getType())
            .address(company.getAddress())
            .build();
    }
}

package com.three_iii.service.application.dto;

import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.CompanyTypeEnum;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyUpdateResponse {

    private UUID id;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyUpdateResponse fromEntity(Company company) {
        return CompanyUpdateResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .type(company.getType())
            .address(company.getAddress())
            .build();
    }
}

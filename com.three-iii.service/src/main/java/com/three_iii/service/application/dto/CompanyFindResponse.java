package com.three_iii.service.application.dto;

import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.CompanyTypeEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFindResponse {

    private UUID id;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyFindResponse fromEntity(Company company) {
        return CompanyFindResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .type(company.getType())
            .address(company.getAddress())
            .build();
    }
}

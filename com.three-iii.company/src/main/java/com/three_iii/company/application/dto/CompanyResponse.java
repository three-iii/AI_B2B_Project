package com.three_iii.company.application.dto;

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

    private UUID id;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyResponse fromEntity(Company company) {
        return CompanyResponse.builder()
            .id(company.getId())
            .name(company.getName())
            .type(company.getType())
            .address(company.getAddress())
            .build();
    }
}

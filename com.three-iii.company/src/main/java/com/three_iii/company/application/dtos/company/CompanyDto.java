package com.three_iii.company.application.dtos.company;

import com.three_iii.company.domain.CompanyTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompanyDto {

    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyDto create(String name, CompanyTypeEnum type, String address) {
        return CompanyDto.builder()
            .name(name)
            .type(type)
            .name(name)
            .address(address)
            .build();
    }
}

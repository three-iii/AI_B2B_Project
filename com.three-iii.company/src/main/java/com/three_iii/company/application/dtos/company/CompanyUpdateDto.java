package com.three_iii.company.application.dtos.company;

import com.three_iii.company.domain.CompanyTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CompanyUpdateDto {

    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyUpdateDto create(String name, CompanyTypeEnum type, String address) {
        return CompanyUpdateDto.builder()
            .name(name)
            .type(type)
            .address(address)
            .build();
    }
}

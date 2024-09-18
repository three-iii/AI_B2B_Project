package com.three_iii.company.application.dtos.company;

import com.three_iii.company.domain.CompanyTypeEnum;
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
public class CompanyDto {

    private UUID hubId;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyDto create(UUID hubId, String name, CompanyTypeEnum type, String address) {
        return CompanyDto.builder()
            .hubId(hubId)
            .name(name)
            .type(type)
            .name(name)
            .address(address)
            .build();
    }
}

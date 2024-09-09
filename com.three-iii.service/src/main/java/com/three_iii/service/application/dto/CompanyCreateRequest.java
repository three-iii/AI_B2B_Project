package com.three_iii.service.application.dto;

import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.CompanyTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateRequest {

    @NotNull
    private String name;
    @NotNull
    private CompanyTypeEnum type;
    @NotNull
    private String address;

    public static Company toEntity(CompanyCreateRequest requestDto) {
        return Company.builder()
            .name(requestDto.getName())
            .type(requestDto.getType())
            .address(requestDto.getAddress())
            .build();
    }
}

package com.three_iii.company.application.dto;

import com.three_iii.company.domain.CompanyTypeEnum;
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
}

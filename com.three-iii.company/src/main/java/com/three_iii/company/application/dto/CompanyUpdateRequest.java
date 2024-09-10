package com.three_iii.company.application.dto;

import com.three_iii.company.domain.CompanyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateRequest {

    private String name;
    private CompanyTypeEnum type;
    private String address;
}

package com.three_iii.company.application.dto;

import com.three_iii.company.domain.CompanyTypeEnum;
import lombok.Getter;

@Getter
public class CompanyUpdateRequest {

    private String name;
    private CompanyTypeEnum type;
    private String address;
}

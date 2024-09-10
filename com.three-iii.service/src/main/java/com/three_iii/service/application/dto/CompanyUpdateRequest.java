package com.three_iii.service.application.dto;

import com.three_iii.service.domain.CompanyTypeEnum;
import lombok.Getter;

@Getter
public class CompanyUpdateRequest {

    private String name;
    private CompanyTypeEnum type;
    private String address;
}

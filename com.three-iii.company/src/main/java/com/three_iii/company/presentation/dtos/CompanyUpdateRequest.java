package com.three_iii.company.presentation.dtos;

import com.three_iii.company.application.dtos.company.CompanyUpdateDto;
import com.three_iii.company.domain.CompanyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateRequest {

    String name;
    CompanyTypeEnum type;
    String address;

    public CompanyUpdateDto toDTO() {
        return CompanyUpdateDto.create(name, type, address);
    }
}

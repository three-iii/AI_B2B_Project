package com.three_iii.company.presentation.dtos;

import com.three_iii.company.application.dtos.company.CompanyDto;
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
    String name;
    @NotNull
    CompanyTypeEnum type;
    @NotNull
    String address;

    public CompanyDto toDTO() {
        return CompanyDto.create(this.name, this.type, this.address);
    }
}

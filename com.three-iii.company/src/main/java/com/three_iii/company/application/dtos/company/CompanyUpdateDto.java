package com.three_iii.company.application.dtos.company;

import com.three_iii.company.domain.CompanyTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CompanyUpdateDto {

    @NotNull
    private UUID hubId;
    private String name;
    private CompanyTypeEnum type;
    private String address;

    public static CompanyUpdateDto create(UUID hubId, String name, CompanyTypeEnum type,
        String address) {
        return CompanyUpdateDto.builder()
            .hubId(hubId)
            .name(name)
            .type(type)
            .address(address)
            .build();
    }
}

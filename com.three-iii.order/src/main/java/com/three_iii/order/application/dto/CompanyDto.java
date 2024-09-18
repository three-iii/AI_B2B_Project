package com.three_iii.order.application.dto;

import com.three_iii.order.domain.CompanyTypeEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID hubId;
    private UUID id;
    private Long userId;
    private String name;
    private CompanyTypeEnum type;
    private String address;
}

package com.three_iii.company.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    @NotNull
    private UUID companyId;
    @NotNull
    private UUID hubId;
    @NotNull
    private String name;
    @NotNull
    private int quantity;

}

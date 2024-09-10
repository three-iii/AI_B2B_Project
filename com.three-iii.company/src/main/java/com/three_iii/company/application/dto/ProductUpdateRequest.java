package com.three_iii.company.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {

    private String name;

    private Integer quantity;

}

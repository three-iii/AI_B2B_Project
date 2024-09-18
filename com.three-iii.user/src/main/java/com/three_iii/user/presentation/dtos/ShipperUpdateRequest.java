package com.three_iii.user.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShipperUpdateRequest {

    private String hubId;
    private String shipperType;


}

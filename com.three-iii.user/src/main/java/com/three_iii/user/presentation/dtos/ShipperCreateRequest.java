package com.three_iii.user.presentation.dtos;

import com.three_iii.user.application.dto.ShipperDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShipperCreateRequest {

    private Long userId;
    private String hubId;
    private String shipperType;

    public ShipperDto toDTO() {
        return ShipperDto.create(userId, hubId, shipperType);
    }

}

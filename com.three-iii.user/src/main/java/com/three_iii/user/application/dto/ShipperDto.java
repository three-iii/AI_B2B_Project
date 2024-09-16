package com.three_iii.user.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipperDto {

    private Long userId;
    private String hubId;
    private String shipperType;

    public static ShipperDto create(Long userId, String hubId, String shipperType) {
        return new ShipperDto(userId, hubId, shipperType);
    }

}

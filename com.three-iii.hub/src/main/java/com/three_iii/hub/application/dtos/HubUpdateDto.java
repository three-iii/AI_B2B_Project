package com.three_iii.hub.application.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HubUpdateDto {

    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String phone_number;

    public static HubUpdateDto create(String name, String address, String latitude,
        String longitude, String phoneNumber) {
        return HubUpdateDto.builder()
            .name(name)
            .name(name)
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .phone_number(phoneNumber)
            .build();
    }
}

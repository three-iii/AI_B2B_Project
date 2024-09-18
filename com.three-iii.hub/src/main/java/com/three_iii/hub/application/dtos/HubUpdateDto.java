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
    private Double latitude;
    private Double longitude;
    private String phone_number;

    public static HubUpdateDto create(String name, String address, Double latitude,
        Double longitude, String phoneNumber) {
        return HubUpdateDto.builder()
            .name(name)
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .phone_number(phoneNumber)
            .build();
    }
}

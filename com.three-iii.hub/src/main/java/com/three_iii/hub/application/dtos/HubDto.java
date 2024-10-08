package com.three_iii.hub.application.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HubDto {

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone_number;

    public static HubDto create(String name, String address, Double latitude, Double longitude,

        String phone_number) {
        return HubDto.builder()
            .name(name)
            .address(address)
            .latitude(latitude)
            .longitude(longitude)
            .phone_number(phone_number)
            .build();
    }

}

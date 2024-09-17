package com.three_iii.hub.application.dtos;

import com.three_iii.hub.domain.Hub;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class HubResponse implements Serializable {

    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone_number;

    public static HubResponse fromEntity(Hub hub) {
        return HubResponse.builder()
            .id(hub.getId())
            .name(hub.getName())
            .address(hub.getAddress())
            .latitude(hub.getLatitude())
            .longitude(hub.getLongitude())
            .phone_number(hub.getPhone_number())
            .build();
    }
}

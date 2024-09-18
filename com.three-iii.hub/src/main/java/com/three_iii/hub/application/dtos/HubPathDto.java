package com.three_iii.hub.application.dtos;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HubPathDto {

    private UUID departureId;
    private UUID arrivalsId;
    private String name; //이동 경로 전시명
    private String timeRequired;

    public static HubPathDto create(UUID departureId, UUID arrivalsId, String name,
        String timeRequired) {
        return HubPathDto.builder()
            .departureId(departureId)
            .arrivalsId(arrivalsId)
            .name(name)
            .timeRequired(timeRequired)
            .build();
    }

}

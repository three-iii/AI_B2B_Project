package com.three_iii.hub.application.dtos;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HubPathUpdateDto {

    private UUID departureId;
    private UUID arrivalsId;
    private String name; //이동 경로 전시명
    private String timeRequired;

    public static HubPathUpdateDto create(UUID departureId, UUID arrivalsId, String name,
        String timeRequired) {
        return HubPathUpdateDto.builder()
            .departureId(departureId)
            .arrivalsId(arrivalsId)
            .name(name)
            .timeRequired(timeRequired)
            .build();
    }
}

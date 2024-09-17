package com.three_iii.hub.application.dtos;

import com.three_iii.hub.domain.HubPath;
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
public class HubPathResponse implements Serializable {

    private UUID id;
    private HubResponse departureId;
    private HubResponse arrivalsId;
    private String name; //이동 경로 전시명
    private String timeRequired;

    public static HubPathResponse fromEntity(HubPath hubPath) {
        return HubPathResponse.builder()
            .id(hubPath.getId())
            .departureId(HubResponse.fromEntity(hubPath.getDepartureId()))
            .arrivalsId(HubResponse.fromEntity(hubPath.getArrivalsId()))
            .name(hubPath.getName())
            .timeRequired(hubPath.getTimeRequired())
            .build();
    }
}

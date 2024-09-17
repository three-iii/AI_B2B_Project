package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubPathDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubPathCreateRequest {

    private UUID departureId;
    private UUID arrivalsId;
    private String name; //이동 경로 전시명
    private String timeRequired;

    public HubPathDto toDTO() {
        return HubPathDto.create(this.departureId, this.arrivalsId, this.name, this.timeRequired);
    }
}

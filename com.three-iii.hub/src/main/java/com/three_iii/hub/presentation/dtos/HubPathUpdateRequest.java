package com.three_iii.hub.presentation.dtos;

import com.three_iii.hub.application.dtos.HubPathUpdateDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubPathUpdateRequest {

    UUID departureId;
    UUID arrivalsId;
    String name; //이동 경로 전시명
    String timeRequired;

    public HubPathUpdateDto toDTO() {
        return HubPathUpdateDto.create(this.departureId, this.arrivalsId, this.name,
            this.timeRequired);
    }
}

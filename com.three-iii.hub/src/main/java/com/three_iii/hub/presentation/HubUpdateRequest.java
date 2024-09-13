package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubUpdateRequest {

    String name;
    String address;
    String latitude;
    String longitude;
    String phone_number;

    public HubUpdateDto toDTO() {
        return HubUpdateDto.create(this.name, this.address, this.latitude, this.longitude,
            this.phone_number);
    }
}

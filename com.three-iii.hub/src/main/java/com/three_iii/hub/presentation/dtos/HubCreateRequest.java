package com.three_iii.hub.presentation.dtos;

import com.three_iii.hub.application.dtos.HubDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubCreateRequest {

    @NotNull
    String name;
    @NotNull
    String address;
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    String phone_number;

    public HubDto toDTO() {
        return HubDto.create(this.name, this.address, this.latitude, this.longitude,
            this.phone_number);
    }
}

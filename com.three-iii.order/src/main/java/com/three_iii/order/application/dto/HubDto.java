package com.three_iii.order.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HubDto {

    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone_number;
}

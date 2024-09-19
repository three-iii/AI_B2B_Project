package com.three_iii.slack.infrastructure;

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
public class HubResponse {

    private UUID id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String phone_number;
}

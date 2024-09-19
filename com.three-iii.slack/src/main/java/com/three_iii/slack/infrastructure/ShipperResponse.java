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
public class ShipperResponse {

    private UUID shipperId;
    private Long userId;
    private String username;
    private UUID hubId;
    private String hubName;
    private String shipperType;
    private String slackId;
}

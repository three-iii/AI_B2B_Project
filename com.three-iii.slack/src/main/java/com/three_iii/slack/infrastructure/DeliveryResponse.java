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
public class DeliveryResponse {

    private UUID deliveryId;
    private UUID originHubId;
    private UUID destinationHubId;
    private String address;
    private String recipientName;
    private UUID shipperId;
}

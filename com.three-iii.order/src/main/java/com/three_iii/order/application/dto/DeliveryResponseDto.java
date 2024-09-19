package com.three_iii.order.application.dto;

import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryStatusEnum;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDto {

    private UUID deliveryId;
    private DeliveryStatusEnum deliveryStatus;
    private UUID originHubId;
    private UUID destinationHubId;
    private String address;
    private String recipientName;
    private UUID shipperId;

    public static DeliveryResponseDto from(Delivery delivery) {
        return DeliveryResponseDto.builder()
            .deliveryId(delivery.getDeliveryId())
            .deliveryStatus(delivery.getStatus())
            .originHubId(delivery.getOriginHubId())
            .destinationHubId(delivery.getDestinationHubId())
            .address(delivery.getAddress())
            .recipientName(delivery.getRecipientName())
            .shipperId(delivery.getShipperId())
            .build();
    }
}

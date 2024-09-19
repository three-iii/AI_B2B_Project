package com.three_iii.order.application.dto;

import com.three_iii.order.domain.DeliveryPath;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPathResponseDto {

    private UUID id;
    private UUID originHubId;
    private UUID destinationHubId;

    public static DeliveryPathResponseDto from(DeliveryPath deliveryPath) {
        return DeliveryPathResponseDto.builder()
            .id(deliveryPath.getId())
            .originHubId(deliveryPath.getOriginHubId())
            .destinationHubId(deliveryPath.getDestinationHubId())
            .build();
    }
}

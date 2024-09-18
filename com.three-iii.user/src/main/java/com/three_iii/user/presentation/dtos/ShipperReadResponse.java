package com.three_iii.user.presentation.dtos;

import com.three_iii.user.domain.Shipper;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipperReadResponse {

    private UUID shipperId;
    private Long userId;
    private String username;
    private UUID hubId;
    private String hubName;
    private String shipperType;
    private String slackId;

    public static ShipperReadResponse fromEntity(Shipper shipper) {
        return new ShipperReadResponse(
            shipper.getId(),
            shipper.getUser().getId(),
            shipper.getUser().getUsername(),
            shipper.getHubId(),
            null,
            shipper.getType().getDescription(),
            shipper.getUser().getSlackId());
    }

    public void updateHubName(String hubName) {
        this.hubName = hubName;
    }

}

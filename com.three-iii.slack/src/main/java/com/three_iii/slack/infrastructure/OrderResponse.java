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
public class OrderResponse {

    private UUID orderItemId;
    private String userName;
    private UUID productionCompanyId;
    private UUID receiptCompanyId;
    private UUID deliveryId;

}

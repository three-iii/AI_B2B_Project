package com.three_iii.slack.infrastructure;

import java.util.List;

public interface DeliveryService {

    List<DeliveryResponse> findAllDeliveryBetweenTime();
}

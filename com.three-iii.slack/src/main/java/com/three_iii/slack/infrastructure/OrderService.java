package com.three_iii.slack.infrastructure;

import java.util.List;

public interface OrderService {

    List<OrderResponse> findAllOrderBetweenTime();
}

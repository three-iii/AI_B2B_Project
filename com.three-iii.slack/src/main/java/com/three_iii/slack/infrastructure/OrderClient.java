package com.three_iii.slack.infrastructure;

import com.three_iii.slack.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", url = "${gateway.url}", configuration = FeignConfig.class)
public interface OrderClient extends OrderService {

    @GetMapping("/api/orders/days")
    List<OrderResponse> findAllOrderBetweenTime();
}

package com.three_iii.slack.infrastructure;

import com.three_iii.slack.exception.Response;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${gateway.url}")
public interface UserClient extends UserService {

    @GetMapping("/api/shippers/{shipperId}")
    Response<ShipperResponse> findShipper(@PathVariable String shipperId);

    @GetMapping("/api/shippers/type?type=HUB_SHIPPER")
    Response<List<ShipperResponse>> findShipperList();
}

package com.three_iii.order.infrastructure;

import com.three_iii.order.application.dto.HubDto;
import com.three_iii.order.config.FeignConfig;
import com.three_iii.order.exception.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hub-service", url = "localhost:19091", configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/api/hubs")
    Response<Page<HubDto>> getAllHubs();

}

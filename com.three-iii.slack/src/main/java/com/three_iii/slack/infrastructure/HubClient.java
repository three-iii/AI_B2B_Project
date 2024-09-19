package com.three_iii.slack.infrastructure;

import com.three_iii.slack.config.FeignConfig;
import com.three_iii.slack.exception.Response;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service", url = "${gateway.url}", configuration = FeignConfig.class)
public interface HubClient extends HubService {

    @GetMapping("/api/hubs/{hubId}")
    Response<HubResponse> findHub(@PathVariable UUID hubId);
}

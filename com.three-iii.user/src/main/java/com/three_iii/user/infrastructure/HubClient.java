package com.three_iii.user.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.three_iii.user.application.dto.HubNameFindRequest;
import com.three_iii.user.config.FeignConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hub-service", url = "${gateway.url}", configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/api/hubs/{hubId}")
    String getHub(@PathVariable("hubId") UUID hubId);

    @PostMapping("/api/hubs/hub-names")
    JsonNode getHubNames(@RequestBody HubNameFindRequest request);
}

package com.three_iii.user.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hub-service")
public interface HubClient {


}

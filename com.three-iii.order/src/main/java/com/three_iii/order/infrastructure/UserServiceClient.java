package com.three_iii.order.infrastructure;

import com.three_iii.order.application.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/users/{userId}")
    UserDto getUserById(@PathVariable Long userId);

    @GetMapping("/api/users/{username}")
    UserDto getUserByUsername(@PathVariable String username);
}

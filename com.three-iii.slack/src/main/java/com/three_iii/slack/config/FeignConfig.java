package com.three_iii.slack.config;

import com.three_iii.slack.util.JwtUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", JwtUtil.createToken(
                0L,
                "scheduler",
                "MASTER_MANAGER"
            ));
        };
    }

}

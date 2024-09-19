package com.three_iii.slack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SlackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackApplication.class, args);
    }

}

package com.three_iii.company.application.service;

import com.three_iii.company.application.dtos.ai.AiResponse;
import com.three_iii.company.presentation.dtos.AiRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models")
public interface AiInterface {

    @PostExchange("{model}:generateContent")
    AiResponse getCompletion(
        @PathVariable String model,
        @RequestBody AiRequest request
    );
}

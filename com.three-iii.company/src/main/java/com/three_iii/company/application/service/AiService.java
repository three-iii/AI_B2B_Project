package com.three_iii.company.application.service;

import com.three_iii.company.application.dtos.ai.AiResponse;
import com.three_iii.company.presentation.dtos.AiRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiService {

    @Qualifier("geminiRestTemplate")
    private final RestTemplate geminiRestTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String getContents(String prompt) {

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        AiRequest request = new AiRequest(prompt);
        AiResponse response = geminiRestTemplate.postForObject(requestUrl, request,
            AiResponse.class);

        return Objects.requireNonNull(response).getCandidates().get(0).getContent().getParts()
            .get(0).getText();
    }
}

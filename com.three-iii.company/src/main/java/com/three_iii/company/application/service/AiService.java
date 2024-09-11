package com.three_iii.company.application.service;

import com.three_iii.company.application.dtos.ai.AiResponse;
import com.three_iii.company.presentation.dtos.AiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    public static final String GEMINI_PRO = "gemini-pro";

    private final AiInterface aiInterface;

    private AiResponse getCompletion(AiRequest request) {
        return aiInterface.getCompletion(GEMINI_PRO, request);
    }

    public String getCompletion(String text) {
        AiRequest geminiRequest = new AiRequest(text + " 답변을 최대한 간결하게 50자 이하로");
        AiResponse response = getCompletion(geminiRequest);

        return response.getCandidates()
            .stream()
            .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                .stream()
                .findFirst()
                .map(AiResponse.Parts::getText))
            .orElse(null);
    }
}

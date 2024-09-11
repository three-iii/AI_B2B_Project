package com.three_iii.company.application.service;

import com.three_iii.company.application.dtos.ai.AiResponse;
import com.three_iii.company.domain.Ai;
import com.three_iii.company.domain.repository.AiRepository;
import com.three_iii.company.presentation.dtos.AiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {

    public static final String GEMINI_PRO = "gemini-pro";

    private final AiInterface aiInterface;
    private final AiRepository aiRepository;

    private AiResponse getCompletion(AiRequest request) {
        return aiInterface.getCompletion(GEMINI_PRO, request);
    }

    public String getContents(String question) {
        AiRequest geminiRequest = new AiRequest(question + " 답변을 최대한 간결하게 50자 이하로");
        AiResponse response = getCompletion(geminiRequest);

        String answer = response.getCandidates()
            .stream()
            .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                .stream()
                .findFirst()
                .map(AiResponse.Parts::getText))
            .orElse(null);

        aiRepository.save(Ai.create(question, answer));

        return answer;
    }
}

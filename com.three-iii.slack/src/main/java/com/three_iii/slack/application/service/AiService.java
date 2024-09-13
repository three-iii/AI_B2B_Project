package com.three_iii.slack.application.service;


import com.three_iii.slack.application.dtos.AiResponse;
import com.three_iii.slack.domain.Ai;
import com.three_iii.slack.domain.repository.AiRepository;
import com.three_iii.slack.presentation.AiRequest;
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
        AiRequest geminiRequest = new AiRequest(question);
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

package com.three_iii.company.presentation.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiRequest {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content {

        private Parts parts;
    }

    @Getter
    @Setter
    public static class Parts {

        private String text;

    }

    @Getter
    @Setter
    public static class GenerationConfig {

        private int candidate_count;
        private int max_output_tokens;
        private double temperature;

    }

    public AiRequest(String prompt) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Parts parts = new Parts();

        parts.setText(prompt + "답변을 최대한 간결하게 50자 이하로");
        content.setParts(parts);

        this.contents.add(content);
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(1);
        this.generationConfig.setMax_output_tokens(30);
        this.generationConfig.setTemperature(0.7);
    }
}

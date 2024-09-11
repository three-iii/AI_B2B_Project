package com.three_iii.company.application.dtos.ai;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AiResponse {

    private List<Candidate> candidates;

    @Getter
    public static class Candidate {

        private Content content;
        private String finishReason;
        private int index;
        private List<SafetyRating> safetyRatings;

    }

    @Getter
    public static class Content {

        private List<Parts> parts;
        private String role;

    }

    @Getter
    public static class Parts {

        private String text;

    }

    @Getter
    public static class SafetyRating {

        private String category;
        private String probability;
    }
}

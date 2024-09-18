package com.three_iii.slack.application.dtos;

import com.three_iii.slack.domain.SlackMessage;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SlackResponse {

    private UUID id;
    private String receiptId;
    private String message;
    private LocalDateTime sentAt;

    public static SlackResponse fromEntity(SlackMessage slackMessage) {
        return SlackResponse.builder()
            .id(slackMessage.getId())
            .receiptId(slackMessage.getReceiptId())
            .message(slackMessage.getMessage())
            .sentAt(slackMessage.getSentAt())
            .build();
    }
}

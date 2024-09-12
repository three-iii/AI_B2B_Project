package com.three_iii.slack.application;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SlackDto {

    private String receiptId;
    private String message;

    public static SlackDto create(String receiptId, String message) {
        return SlackDto.builder()
            .receiptId(receiptId)
            .message(message)
            .build();
    }

}

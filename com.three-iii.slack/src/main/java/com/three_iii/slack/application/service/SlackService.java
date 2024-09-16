package com.three_iii.slack.application.service;

import static com.three_iii.slack.exception.ErrorCode.ERROR_SENDING_MESSAGE;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.three_iii.slack.application.dtos.SlackDto;
import com.three_iii.slack.application.dtos.SlackResponse;
import com.three_iii.slack.domain.SlackMessage;
import com.three_iii.slack.domain.repository.SlackRepository;
import com.three_iii.slack.exception.ApplicationException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackRepository slackRepository;

    @Value("${slack.token}")
    private String slackToken;

    @Value("${slack.id}")
    private String slackChannel;

    public SlackResponse createSlackMessage(SlackDto request)
        throws IOException, SlackApiException {
        Slack slack = Slack.getInstance();
        ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder()
            .channel(request.getReceiptId()) //수신인 슬랙 ID, 같은 워크스페이스에서 작동
            .text(request.getMessage())
            .build();

        ChatPostMessageResponse response = slack.methods(slackToken)
            .chatPostMessage(chatPostMessageRequest);

        if (!response.isOk()) {
            throw new ApplicationException(ERROR_SENDING_MESSAGE);
        }

        SlackMessage slackMessage = SlackMessage.create(request);
        return SlackResponse.fromEntity(slackRepository.save(slackMessage));
    }
}

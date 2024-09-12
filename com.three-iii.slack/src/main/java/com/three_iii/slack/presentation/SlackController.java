package com.three_iii.slack.presentation;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.SlackResponse;
import com.three_iii.slack.application.SlackService;
import com.three_iii.slack.exception.Response;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping("/slack-messages")
    public Response<SlackResponse> createSlackMessage(
        @RequestBody @Valid SlackCreateRequest request)
        throws SlackApiException, IOException {
        return Response.success(slackService.createSlackMessage(request.toDTO()));
    }
}

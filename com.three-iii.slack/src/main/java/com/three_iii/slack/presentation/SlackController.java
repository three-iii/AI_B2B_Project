package com.three_iii.slack.presentation;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.dtos.SlackResponse;
import com.three_iii.slack.application.service.SlackService;
import com.three_iii.slack.application.service.WeatherService;
import com.three_iii.slack.exception.Response;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SlackController {

    private final SlackService slackService;
    private final WeatherService weatherService;

    @PostMapping("/slack-messages")
    public Response<SlackResponse> createSlackMessage(
        @RequestBody @Valid SlackCreateRequest request)
        throws SlackApiException, IOException {

        return Response.success(slackService.createSlackMessage(request.toDTO()));
    }

//    @GetMapping
//    public List<Item> getWeather() {
//        return weatherService.getWeather();
//    }
}

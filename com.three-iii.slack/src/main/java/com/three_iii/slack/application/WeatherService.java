package com.three_iii.slack.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.serviceKey}")
    String serviceKey;

    private final WeatherInterface weatherInterface;

    private WeatherResponse getCompletion() {
        return weatherInterface.getCompletion(serviceKey);
    }

    public WeatherResponse getWeather() {
        return getCompletion();
    }

}

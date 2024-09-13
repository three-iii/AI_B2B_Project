package com.three_iii.slack.application.service;

import com.three_iii.slack.application.dtos.WeatherResponse;
import com.three_iii.slack.application.dtos.WeatherResponse.Item;
import java.util.List;
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

    private WeatherResponse getWeatherDetail() {
        return weatherInterface.getCompletion(serviceKey);
    }

    public String getWeather() {
        List<Item> items = getWeatherDetail().getResponse().getBody().getItems().getItem();

        StringBuilder sb = new StringBuilder();
        for (WeatherResponse.Item item : items) {
            if (item.getCategory().equals("TMN")) {
                sb.append("최저기은:").append(item.getFcstValue()).append(" ");
            }
            if (item.getCategory().equals("TMX")) {
                sb.append("최고기은:").append(item.getFcstValue());
            }
        }
        return sb.toString();
    }

}

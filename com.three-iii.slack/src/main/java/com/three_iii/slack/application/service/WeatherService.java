package com.three_iii.slack.application.service;

import com.three_iii.slack.application.dtos.WeatherResponse;
import com.three_iii.slack.application.dtos.WeatherResponse.Item;
import java.util.ArrayList;
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

    public List<WeatherResponse.Item> getWeather() {
        List<Item> items = getWeatherDetail().getResponse().getBody().getItems().getItem();

        List<WeatherResponse.Item> list = new ArrayList<>();
        for (WeatherResponse.Item item : items) {
            if (item.getCategory().equals("TMX") || //최고 온도
                item.getCategory().equals("TMN") || //최저 온도
                item.getCategory().equals("POP")    //강수 확률
            ) {
                list.add(item);
            }
        }

        return list;
    }

}

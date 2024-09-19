package com.three_iii.slack.application.service;

import com.three_iii.slack.application.dtos.WeatherResponse;
import com.three_iii.slack.application.dtos.WeatherResponse.Item;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now().minusDays(1);

        // yyyyMMdd 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        log.info("어제 날짜 {}", formattedDate);

        return weatherInterface.getCompletion(serviceKey, formattedDate);
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

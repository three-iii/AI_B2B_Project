package com.three_iii.slack.application.service;

import com.three_iii.slack.application.dtos.WeatherResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface WeatherInterface {

    //그날 하루의 날씨 정보를 받으려면 전날 23시로 설정
    //numOfRows를 266으로 설정하면 날씨 정보를 모두 받아오고 최고기온과 최저기온값을 받아온다.
    @GetExchange("/getVilageFcst?serviceKey={serviceKey}&pageNo=1&numOfRows=266&dataType=JSON&base_date={base_date}&base_time=2300&nx=55&ny=127")
    WeatherResponse getCompletion(
        @PathVariable("serviceKey") String serviceKey,
        @PathVariable("base_date") String base_date
    );
}

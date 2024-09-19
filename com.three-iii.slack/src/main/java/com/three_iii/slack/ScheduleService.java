package com.three_iii.slack;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.service.AiService;
import com.three_iii.slack.application.service.SlackService;
import com.three_iii.slack.application.service.WeatherService;
import com.three_iii.slack.infrastructure.DeliveryResponse;
import com.three_iii.slack.infrastructure.DeliveryService;
import com.three_iii.slack.infrastructure.UserService;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
public class ScheduleService {

    private final SlackService slackService;
    private final WeatherService weatherService;
    private final AiService aiService;
    //private final UserService userService;
    private final DeliveryService deliveryService;

    public ScheduleService(SlackService slackService, WeatherService weatherService,
        AiService aiService, UserService userService,
        @Lazy DeliveryService deliveryService) { //순환 참조 문제로 @Lazy 어노테이션 사용
        this.slackService = slackService;
        this.weatherService = weatherService;
        this.aiService = aiService;
        //this.userService = userService;
        this.deliveryService = deliveryService;
    }


    // 업체 배송 담담자에게 날씨와 배송 정보 알림 처리
    // 배송 테이블의 출발 업체 배송자 id에게 알림처리
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 6 * * *") //매일 6시에 실행
    public void companyShipperSchedule() throws SlackApiException, IOException {
        List<DeliveryResponse> deliveryResponseList = deliveryService.findAllDeliveryBetweenTime();
        for (DeliveryResponse deliveryResponse : deliveryResponseList) {
            System.out.println("실행 중!!!!" + deliveryResponse.getShipperId());//배송자 id
            //배송자 id로 slackid 가져오기
        }
        //String weatherInfo = weatherService.getWeather();
        //log.info("날씨 {}", weatherInfo);

        //slackService.createSlackMessage(new SlackDto("U07MM562S56", "메시지 테스트 입니다"));
    }

    // 허브 배송 담당자에게 허브별 주문 사항에 대한 알림 처리
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 8 * * *") //매일 9시에 실행
    public void hubShipperSchedule() throws SlackApiException, IOException {
        //slackService.createSlackMessage(new SlackDto("U07MM562S56", "메시지 테스트 입니다"));
    }

}

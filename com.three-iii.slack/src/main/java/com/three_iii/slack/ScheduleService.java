package com.three_iii.slack;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.dtos.SlackDto;
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
    private final UserService userService;
    private final DeliveryService deliveryService;

    public ScheduleService(SlackService slackService, WeatherService weatherService,
        AiService aiService, UserService userService,
        @Lazy DeliveryService deliveryService) { //순환 참조 문제로 @Lazy 어노테이션 사용
        this.slackService = slackService;
        this.weatherService = weatherService;
        this.aiService = aiService;
        this.userService = userService;
        this.deliveryService = deliveryService;
    }

    // 업체 배송 담담자에게 날씨와 배송 정보 알림 처리
    // 배송 테이블의 출발 업체 배송자 id에게 알림처리
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 6 * * *") //매일 6시에 실행
    public void companyShipperSchedule() throws SlackApiException, IOException {
        String weatherInfo = weatherService.getWeather();

        List<DeliveryResponse> deliveryResponseList = deliveryService.findAllDeliveryBetweenTime();
        for (DeliveryResponse deliveryResponse : deliveryResponseList) {
            log.info("배송자 UUID {}", deliveryResponse.getShipperId());
            String slackId = userService.findShipper(deliveryResponse.getShipperId().toString())
                .getResult().getSlackId();
            log.info("slack id {}", slackId);
            log.info("날씨 {}", weatherInfo);
            String message = aiService.getContents(
                "날씨 정보와 배송 정보를 50자 이내의 문장으로 요약해줘\n"
                    + weatherInfo + "\n"
                    + "배송지 주소: " + deliveryResponse.getAddress() + "\n"
                    + "수령인: " + deliveryResponse.getRecipientName());
            log.info("메세지 {}", message);
            slackService.createSlackMessage(new SlackDto(slackId, message));
        }

    }

    // 허브 배송 담당자에게 허브별 주문 사항에 대한 알림 처리
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 8 * * *") //매일 9시에 실행
    public void hubShipperSchedule() throws SlackApiException, IOException {
        //slackService.createSlackMessage(new SlackDto("U07MM562S56", "메시지 테스트 입니다"));
    }

}

package com.three_iii.slack;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.service.SlackService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final SlackService slackService;

    // 업체 배송 담담자에게 날씨와 배송 정보 알림 처리
    // 배송 테이블의 출발 업체 배송자 id에게 알림처리
    //
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 6 * * *") //매일 6시에 실행
    public void companyShipperSchedule() throws SlackApiException, IOException {
        //slackService.createSlackMessage(new SlackDto("U07MM562S56", "메시지 테스트 입니다"));
    }

    // 허브 배송 담당자에게 허브별 주문 사항에 대한 알림 처리
    @Scheduled(cron = " 0 0/1 * * * *")
    //@Scheduled(cron = " 0 0 8 * * *") //매일 9시에 실행
    public void hubShipperSchedule() throws SlackApiException, IOException {
        //slackService.createSlackMessage(new SlackDto("U07MM562S56", "메시지 테스트 입니다"));
    }
}

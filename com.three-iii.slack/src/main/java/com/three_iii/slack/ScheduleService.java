package com.three_iii.slack;

import com.slack.api.methods.SlackApiException;
import com.three_iii.slack.application.dtos.SlackDto;
import com.three_iii.slack.application.service.AiService;
import com.three_iii.slack.application.service.SlackService;
import com.three_iii.slack.application.service.WeatherService;
import com.three_iii.slack.infrastructure.CompanyResponse;
import com.three_iii.slack.infrastructure.CompanyService;
import com.three_iii.slack.infrastructure.DeliveryResponse;
import com.three_iii.slack.infrastructure.DeliveryService;
import com.three_iii.slack.infrastructure.HubResponse;
import com.three_iii.slack.infrastructure.HubService;
import com.three_iii.slack.infrastructure.OrderResponse;
import com.three_iii.slack.infrastructure.OrderService;
import com.three_iii.slack.infrastructure.ShipperResponse;
import com.three_iii.slack.infrastructure.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {

    private final SlackService slackService;
    private final WeatherService weatherService;
    private final AiService aiService;
    private final UserService userService;
    private final DeliveryService deliveryService;
    private final OrderService orderService;
    private final CompanyService companyService;
    private final HubService hubService;

    public ScheduleService(SlackService slackService, WeatherService weatherService,
        AiService aiService, UserService userService,
        @Lazy DeliveryService deliveryService,
        OrderService orderService, CompanyService companyService,
        HubService hubService) { //순환 참조 문제로 @Lazy 어노테이션 사용
        this.slackService = slackService;
        this.weatherService = weatherService;
        this.aiService = aiService;
        this.userService = userService;
        this.deliveryService = deliveryService;
        this.orderService = orderService;
        this.companyService = companyService;
        this.hubService = hubService;
    }

    // 업체 배송 담담자에게 날씨와 배송 정보 알림 처리
    // 배송 테이블의 출발 업체 배송자 id에게 알림처리
    //@Scheduled(cron = " 0 0/1 * * * *")
    @Scheduled(cron = " 0 0 6 * * *") //매일 6시에 실행
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
                "다음 내용을 모두 포함해서 배송담당자가 알아보기 쉽게 메세지를 생성해줘\n"
                    + weatherInfo + "\n"
                    + "배송지 주소: " + deliveryResponse.getAddress() + "\n"
                    + "수령인: " + deliveryResponse.getRecipientName());
            log.info("ai가 생성한 메세지: {}", message);
            slackService.createSlackMessage(new SlackDto(slackId, message));
        }

    }

    // 허브 배송 담당자에게 허브별 주문 사항에 대한 알림 처리
    //@Scheduled(cron = " 0 0/1 * * * *")
    @Scheduled(cron = " 0 0 8 * * *") //매일 9시에 실행
    public void hubShipperSchedule() throws SlackApiException, IOException {
        List<OrderResponse> orderResponseList = orderService.findAllOrderBetweenTime();
        // 허브ID별로 주문을 그룹화하기 위한 맵
        Map<UUID, List<OrderResponse>> ordersByHub = new HashMap<>();
        for (OrderResponse orderResponse : orderResponseList) {
            CompanyResponse companyResponse = companyService.findCompany(
                orderResponse.getProductionCompanyId()).getResult();
            log.info("getProductionCompanyId {}", orderResponse.getProductionCompanyId());
            log.info("companyId {}", companyResponse.getId());
            log.info("hubId {}", companyResponse.getHubId());

            ordersByHub.computeIfAbsent(companyResponse.getHubId(), k -> new ArrayList<>())
                .add(orderResponse);
        }

        StringBuilder sb = new StringBuilder();
        ordersByHub.forEach(((uuid, orderResponses) -> {
            HubResponse hubResponse = hubService.findHub(uuid).getResult();
            String message = aiService.getContents(
                "다음 내용을 모두 포함해서 배송담당자가 알아보기 쉽게 메세지를 생성해줘\n"
                    + "허브 이름:" + hubResponse.getName() + "\n"
                    + "허브 주소: " + hubResponse.getAddress() + "\n"
                    + "허브 전화번호: " + hubResponse.getPhone_number() + "\n"
                    + "총 주문 건수: " + orderResponses.size());
            log.info("ai가 생성한 메세지: {}", message);
            sb.append(message);
        }));

        //허브 배송 담당자 리스트 가져오기
        List<ShipperResponse> shipperList = userService.findShipperList().getResult();

        for (ShipperResponse shipperResponse : shipperList) {
            slackService.createSlackMessage(
                new SlackDto(shipperResponse.getSlackId(), sb.toString()));
        }
    }

}

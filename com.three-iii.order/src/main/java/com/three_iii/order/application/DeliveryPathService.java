package com.three_iii.order.application;

import com.three_iii.order.application.dto.DeliveryPathResponseDto;
import com.three_iii.order.domain.DeliveryPath;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.domain.repository.DeliveryPathRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryPathService {

    private final DeliveryPathRepository deliveryPathRepository;

    public DeliveryPathResponseDto findDeliveryPath(UUID shippingId, UserPrincipal userPrincipal) {
        DeliveryPath deliveryPath = deliveryPathRepository.findById(shippingId).orElseThrow(() -> {
            log.error("배달 정보를 찾을 수 없습니다.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "배달 정보를 찾을 수 없습니다.");
        });

        return DeliveryPathResponseDto.from(deliveryPath);
    }
}

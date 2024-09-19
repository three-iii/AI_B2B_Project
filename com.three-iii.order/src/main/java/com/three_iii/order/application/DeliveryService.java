package com.three_iii.order.application;

import com.three_iii.order.application.dto.DeliveryResponseDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryStatusEnum;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.domain.repository.DeliveryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Page<DeliveryResponseDto> findAllDelivery(String keyword, Pageable pageable,
        UserPrincipal userPrincipal) {

        String role = userPrincipal.getRole();

        if (role.equals("SHIPPER")) {
            return deliveryRepository.searchDelivery(keyword, userPrincipal.getUsername(),
                pageable);
        } else {
            return deliveryRepository.searchDelivery(keyword, null, pageable);
        }
    }

    // 배송 단건 조회
    public DeliveryResponseDto findDelivery(UUID shippingId, UserPrincipal userPrincipal) {
        Delivery delivery = deliveryRepository.findById(
                shippingId)
            .orElseThrow(() -> {
                log.error("배달 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "배달 정보를 찾을 수 없습니다.");
            });

        if (!delivery.getOrder().getUserName().equals(userPrincipal.getUsername())
            || userPrincipal.getRole().equals("SHIPPER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return DeliveryResponseDto.from(delivery);
    }

    // 배송 상태 변경
    @Transactional
    public Delivery updateDeliveryStatus(UUID shippingId, DeliveryStatusEnum newStatus) {
        Delivery delivery = deliveryRepository.findById(shippingId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 배송 아이디 입니다."));

        delivery.setStatus(newStatus);

        return deliveryRepository.save(delivery);
    }

    // 배송 상태 조회
    @Transactional(readOnly = true)
    public Delivery findDeliveryStatus(UUID shippingId) {
        return deliveryRepository.findById(shippingId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 배송 아이디 입니다."));
    }

    public List<DeliveryResponseDto> findAllDeliveryBetweenTime() {
        // 24시간 범위로 주문 조회
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        List<Delivery> deliveries = deliveryRepository.findAllByCreatedAtBetween(startTime,
            endTime);

        return deliveries.stream()
            .map(DeliveryResponseDto::from)
            .collect(Collectors.toList());
    }
}

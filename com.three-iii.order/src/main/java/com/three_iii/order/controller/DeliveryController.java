package com.three_iii.order.controller;

import com.three_iii.order.application.DeliveryService;
import com.three_iii.order.application.dto.DeliveryResponseDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryStatusEnum;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shippings")
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 전체조회
    @GetMapping
    public Response<Page<DeliveryResponseDto>> findAllDelivery(
        @RequestParam(required = false) String keyword, Pageable pageable,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(deliveryService.findAllDelivery(keyword, pageable, userPrincipal));
    }

    // 배송 단건 조회
    @GetMapping("/{shippingId}")
    @Operation(summary = "배송 단건 조회")
    public Response<DeliveryResponseDto> findDelivery(
        @PathVariable(name = "shippingId") UUID shippingId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(deliveryService.findDelivery(shippingId, userPrincipal));
    }

    // 배송 상태 변경
    @PatchMapping("/{shippingId}/status")
    public Response<DeliveryResponseDto> updateDeliveryStatus(
        @PathVariable UUID shippingId,
        @RequestBody DeliveryStatusEnum newStatus) {

        Delivery updatedDelivery = deliveryService.updateDeliveryStatus(shippingId, newStatus);
        DeliveryResponseDto responseDto = DeliveryResponseDto.from(updatedDelivery);

        return Response.success(responseDto);
    }

    // 배송 상태 조회
    @GetMapping("/{shippingId}/status")
    public Response<DeliveryResponseDto> findDeliveryStatus(@PathVariable UUID shippingId) {
        Delivery delivery = deliveryService.findDeliveryStatus(shippingId);
        DeliveryResponseDto responseDto = DeliveryResponseDto.from(delivery);

        return Response.success(responseDto);
    }

    // 스케줄러용 호출 api
    @GetMapping("/days")
    public List<DeliveryResponseDto> findAllDeliveryBetweenTime() {
        return deliveryService.findAllDeliveryBetweenTime();
    }
}

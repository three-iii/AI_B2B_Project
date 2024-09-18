package com.three_iii.order.controller;

import com.three_iii.order.application.DeliveryService;
import com.three_iii.order.application.dto.DeliveryResponseDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryStatusEnum;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shippings")
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 전체조회

    // 배송 단건 조회
    @GetMapping("/{shippingId}")
    @Operation(summary = "배송 단건 조회")
    public Response<DeliveryResponseDto> findDelivery(
        @PathVariable(name = "shippingId") UUID shippingId,
        @RequestHeader UserPrincipal userPrincipal) {
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
}

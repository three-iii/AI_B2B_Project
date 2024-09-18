package com.three_iii.service.controller;

import com.three_iii.service.application.OrderService;
import com.three_iii.service.application.dto.order.OrderRequestDto;
import com.three_iii.service.application.dto.order.OrderResponseDto;
import com.three_iii.service.application.dto.order.OrderUpdateRequestDto;
import com.three_iii.service.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 추가")
    public ResponseEntity<OrderResponseDto> createOrder(
        @RequestBody OrderRequestDto requestDto,
        @RequestHeader(name = "X-User-Name", required = false) User user) {
        return ResponseEntity.ok(orderService.createOrder(requestDto, user));
    }

    @GetMapping("{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ResponseEntity<OrderResponseDto> findOrder(
        @PathVariable(name = "orderId") UUID orderId,
        @RequestHeader(name = "X-User-Name", required = false) User user) {
        return ResponseEntity.ok(orderService.findOrder(orderId, user));
    }

    @PatchMapping
    @Operation(summary = "주문 수정")
    public ResponseEntity<OrderResponseDto> updateOrder(
        @RequestBody OrderUpdateRequestDto orderUpdateRequestDto,
        @RequestHeader(name = "X-User-Name", required = false) User user) {

        return ResponseEntity.ok(orderService.updateOrder(orderUpdateRequestDto, user));
    }

    @PatchMapping("{orderId}/cancel")
    @Operation(summary = "주문 취소")
    public ResponseEntity<String> cancelOrder(
        @PathVariable UUID orderId,
        @RequestHeader User user) {

        orderService.cancelOrder(orderId, user);
        return ResponseEntity.ok("주문 취소 성공");
    }

    @DeleteMapping("{orderId}")
    @Operation(summary = "주문 삭제")
    public ResponseEntity<String> deleteOrder(
        @PathVariable(name = "orderId") UUID orderId,
        @RequestHeader(name = "X-User-Name", required = false) User user) {

        orderService.deleteOrder(orderId, user);
        return ResponseEntity.ok("주문 삭제 성공");
    }
}

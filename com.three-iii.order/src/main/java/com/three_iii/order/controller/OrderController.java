package com.three_iii.order.controller;


import com.three_iii.order.application.OrderService;
import com.three_iii.order.application.dto.OrderRequestDto;
import com.three_iii.order.application.dto.OrderResponseDto;
import com.three_iii.order.application.dto.OrderUpdateRequestDto;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "주문 전체 조회")
    public Response<Page<OrderResponseDto>> findAllOrder(
        @RequestParam(value = "keyword", required = false) String keyword, Pageable pageable,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(orderService.findAllOrder(keyword, pageable, userPrincipal));
    }

    @PostMapping
    @Operation(summary = "주문 추가")
    public ResponseEntity<OrderResponseDto> createOrder(
        @RequestBody OrderRequestDto requestDto,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.createOrder(requestDto, userPrincipal));
    }

    @GetMapping("{orderId}")
    @Operation(summary = "주문 단건 조회")
    public ResponseEntity<OrderResponseDto> findOrder(
        @PathVariable(name = "orderId") UUID orderId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.findOrder(orderId, userPrincipal));
    }

    @PatchMapping
    @Operation(summary = "주문 수정")
    public ResponseEntity<OrderResponseDto> updateOrder(
        @RequestBody OrderUpdateRequestDto orderUpdateRequestDto,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return ResponseEntity.ok(orderService.updateOrder(orderUpdateRequestDto, userPrincipal));
    }

    @PatchMapping("{orderId}/cancel")
    @Operation(summary = "주문 취소")
    public ResponseEntity<String> cancelOrder(
        @PathVariable UUID orderId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {

        orderService.cancelOrder(orderId, userPrincipal);
        return ResponseEntity.ok("주문 취소 성공");
    }

    @DeleteMapping("{orderId}")
    @Operation(summary = "주문 삭제")
    public ResponseEntity<String> deleteOrder(
        @PathVariable(name = "orderId") UUID orderId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {

        orderService.deleteOrder(orderId, userPrincipal);
        return ResponseEntity.ok("주문 삭제 성공");
    }

    // 스케줄러용 호출 api
    @GetMapping("/days")
    public List<OrderResponseDto> findAllOrderBetweenTime() {
        return orderService.findAllOrderBetweenTime();
    }
}

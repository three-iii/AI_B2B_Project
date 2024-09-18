package com.three_iii.service.application.dto.order;

import com.three_iii.service.domain.Order;
import com.three_iii.service.domain.OrderStatusEnum;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID orderId;
    private Long userId;
    private UUID requestCompanyId;
    private UUID receiveCompanyId;
    private List<OrderItemRequestDto> orderItems;
    private UUID deliveryId;
    private OrderStatusEnum status;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
            .orderId(order.getOrderItemId())
            .userId(order.getUserId())
            .requestCompanyId(order.getRequestCompany().getRequestCompanyId())
            .orderItems(OrderItemRequestDto.from(order.getOrderItems()))
            .deliveryId(order.getDeliveryId())
            .orderStatusEnum(order.getStatus())
            .build();
    }
}

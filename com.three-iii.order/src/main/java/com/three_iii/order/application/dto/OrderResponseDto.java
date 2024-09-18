package com.three_iii.order.application.dto;

import com.three_iii.order.domain.Order;
import com.three_iii.order.domain.OrderStatusEnum;
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

    private UUID orderItemId;
    private Long userId;
    private UUID productionCompanyId;
    private UUID receiptCompanyId;
    private List<OrderItemRequestDto> orderItems;
    private UUID deliveryId;
    private OrderStatusEnum status;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
            .orderItemId(order.getOrderItemId())
            .userId(order.getUserId())
            .productionCompanyId(order.getProductionCompanyId())
            .receiptCompanyId(order.getReceiptCompanyId())
            .orderItems(OrderItemRequestDto.from(order.getOrderItems()))
            .deliveryId(order.getDelivery().getDeliveryId())
            .status(order.getStatus())
            .build();
    }
}

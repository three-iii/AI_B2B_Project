package com.three_iii.order.application.dto;

import com.three_iii.order.domain.OrderItem;
import java.util.ArrayList;
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
public class OrderItemRequestDto {

    private UUID productId;
    private Integer productQuantity;

    public static OrderItemRequestDto from(OrderItem orderItem) {
        return OrderItemRequestDto.builder()
            .productId(orderItem.getProductId())
            .productQuantity(orderItem.getQuantity())
            .build();
    }

    public static List<OrderItemRequestDto> from(List<OrderItem> orderItems) {
        List<OrderItemRequestDto> orderItemRequestDto = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemRequestDto.add(OrderItemRequestDto.from(orderItem));
        }
        return orderItemRequestDto;
    }
}

package com.three_iii.order.application.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDto {

    private UUID orderId;
    private List<OrderItemRequestDto> orderItemRequestDto;
}

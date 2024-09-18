package com.three_iii.service.application.dto.order;

import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.Order;
import com.three_iii.service.domain.OrderStatusEnum;
import com.three_iii.service.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private UUID requestCompanyId;
    private UUID deliveryId;
    private String shipping;
    private List<OrderItemRequestDto> orderItemRequestDto;
    private OrderStatusEnum status;

    public Order toOrder(User user, Company company) {
        return Order.builder()
            .OrderItemId(UUID.randomUUID())
            .company(company)
            .orderItems(new ArrayList<>())
            .userId(user.getId())
            .deliveryId(this.deliveryId)
            .status(this.status)
            .build();
    }
}

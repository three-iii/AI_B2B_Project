package com.three_iii.order.application.dto;


import com.three_iii.order.domain.Order;
import com.three_iii.order.domain.OrderStatusEnum;
import com.three_iii.order.domain.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrderRequestDto {

    private UUID productionCompanyId;
    private UUID receiptCompanyId;
    private String userName;
    private String deliveryAddress;
    private String recipientName;
    private String slackId;
    private List<OrderItemRequestDto> orderItemRequestDto;
    private OrderStatusEnum status;


    public Order toOrder(UserPrincipal userPrincipal, UUID productionCompany, UUID receiptCompany) {
        return Order.builder()
            .id(UUID.randomUUID())
            .userName(userPrincipal.getUsername())
            .productionCompanyId(productionCompany)
            .receiptCompanyId(receiptCompany)
            .recipientName(this.recipientName)
            .slackId(this.slackId)
            .status(OrderStatusEnum.COMPLETED)
            .orderItems(new ArrayList<>())
            .build();
    }


    public void validate() {
        if (productionCompanyId == null) {
            log.error("Production Company ID cannot be null");
            throw new IllegalArgumentException("Production Company ID cannot be null");
        }
        if (receiptCompanyId == null) {
            log.error("Receipt Company ID cannot be null");
            throw new IllegalArgumentException("Receipt Company ID cannot be null");
        }
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            log.error("Delivery Address cannot be null or empty");
            throw new IllegalArgumentException("Delivery Address cannot be null or empty");
        }
        if (recipientName == null || recipientName.trim().isEmpty()) {
            log.error("Recipient Name cannot be null or empty");
            throw new IllegalArgumentException("Recipient Name cannot be null or empty");
        }
        if (orderItemRequestDto == null || orderItemRequestDto.isEmpty()) {
            log.error("Order items cannot be null or empty");
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }
    }


}

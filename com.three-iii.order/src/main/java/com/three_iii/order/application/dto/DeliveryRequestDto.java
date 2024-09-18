package com.three_iii.order.application.dto;

import com.three_iii.order.domain.DeliveryStatusEnum;
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
public class DeliveryRequestDto {

    private UUID requestCompanyId;
    private List<OrderItemRequestDto> orderItemRequestDto;
    private DeliveryStatusEnum statusEnum;
}

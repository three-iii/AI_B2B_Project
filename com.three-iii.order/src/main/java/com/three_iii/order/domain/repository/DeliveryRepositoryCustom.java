package com.three_iii.order.domain.repository;

import com.three_iii.order.application.dto.DeliveryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepositoryCustom {

    Page<DeliveryResponseDto> searchDelivery(String keyword, String userName, Pageable pageable);
}

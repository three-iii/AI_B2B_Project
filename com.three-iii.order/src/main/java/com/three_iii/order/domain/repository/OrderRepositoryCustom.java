package com.three_iii.order.domain.repository;

import com.three_iii.order.application.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderResponseDto> searchOrder(String keyword, String userName, Pageable pageable);
}

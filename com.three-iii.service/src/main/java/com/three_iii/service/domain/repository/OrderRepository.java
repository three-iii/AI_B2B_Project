package com.three_iii.service.domain.repository;

import com.three_iii.service.domain.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findOneByOrderIdAndDeletedAtIsNull(UUID orderId);
}

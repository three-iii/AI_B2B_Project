package com.three_iii.order.domain.repository;

import com.three_iii.order.domain.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom {

    Optional<Order> findOneByOrderItemsOrderItemIdAndDeletedAtIsNull(UUID orderItemId);


}

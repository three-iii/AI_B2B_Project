package com.three_iii.order.domain.repository;

import com.three_iii.order.domain.Delivery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID>,
    DeliveryRepositoryCustom {

    // 24시간 동안의 주문을 찾기 위한 쿼리
    List<Delivery> findAllByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}

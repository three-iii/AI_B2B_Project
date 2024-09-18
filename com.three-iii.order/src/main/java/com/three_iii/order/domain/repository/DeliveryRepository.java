package com.three_iii.order.domain.repository;

import com.three_iii.order.domain.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID>,
    DeliveryRepositoryCustom {

}

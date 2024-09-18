package com.three_iii.order.domain.repository;

import com.three_iii.order.domain.DeliveryPath;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPathRepository extends JpaRepository<DeliveryPath, UUID> {

}

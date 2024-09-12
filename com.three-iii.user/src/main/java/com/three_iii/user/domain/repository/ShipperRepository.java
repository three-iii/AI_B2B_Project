package com.three_iii.user.domain.repository;

import com.three_iii.user.domain.Shipper;
import com.three_iii.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, UUID> {

    boolean existsByUser(User user);
}

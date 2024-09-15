package com.three_iii.user.domain.repository;

import com.three_iii.user.domain.Shipper;
import com.three_iii.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, UUID> {

    boolean existsByUser(User user);

    @Query("SELECT s FROM Shipper s JOIN FETCH s.user")
    List<Shipper> findAll();
}

package com.three_iii.hub.domain.repository;

import com.three_iii.hub.domain.Hub;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID> {

}

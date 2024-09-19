package com.three_iii.hub.domain.repository;

import com.three_iii.hub.domain.Hub;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRepository extends JpaRepository<Hub, UUID>, HubRepositoryCustom {

    List<Hub> findByIdIn(List<UUID> ids);
}

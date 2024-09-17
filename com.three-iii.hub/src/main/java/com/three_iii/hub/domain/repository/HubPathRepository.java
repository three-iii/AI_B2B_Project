package com.three_iii.hub.domain.repository;

import com.three_iii.hub.domain.HubPath;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubPathRepository extends JpaRepository<HubPath, UUID> {

}

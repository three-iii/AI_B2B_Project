package com.three_iii.company.domain.repository;

import com.three_iii.company.domain.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {

}

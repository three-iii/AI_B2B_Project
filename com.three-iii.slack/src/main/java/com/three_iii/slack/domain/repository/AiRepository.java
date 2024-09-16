package com.three_iii.slack.domain.repository;

import com.three_iii.slack.domain.Ai;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRepository extends JpaRepository<Ai, UUID> {

}

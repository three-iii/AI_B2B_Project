package com.three_iii.slack.domain.repository;

import com.three_iii.slack.domain.SlackMessage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackRepository extends JpaRepository<SlackMessage, UUID> {

}

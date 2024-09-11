package com.three_iii.user.domain.repository;

import com.three_iii.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsBySlackId(String slackId);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}

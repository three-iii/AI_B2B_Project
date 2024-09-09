package com.three_iii.service.domain.repository;

import com.three_iii.service.domain.Company;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByName(String name);
}

package com.three_iii.company.domain.repository;

import com.three_iii.company.domain.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {

    Boolean existsByName(String name);
}

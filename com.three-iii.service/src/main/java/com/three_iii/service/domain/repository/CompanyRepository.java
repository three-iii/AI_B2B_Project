package com.three_iii.service.domain.repository;

import com.three_iii.service.domain.Company;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {

    Boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Company a SET a.is_delete = true WHERE a.id = :companyId")
    void delete(@Param("companyId") UUID companyId);
}

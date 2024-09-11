package com.three_iii.company.domain.repository;

import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {

    boolean existsByNameAndCompany(String name, Company company);
}

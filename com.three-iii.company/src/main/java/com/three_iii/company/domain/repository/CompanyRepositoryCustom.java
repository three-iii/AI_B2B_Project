package com.three_iii.company.domain.repository;

import com.three_iii.company.application.dto.CompanyFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    Page<CompanyFindResponse> searchCompany(String keyword, Pageable pageable);

}

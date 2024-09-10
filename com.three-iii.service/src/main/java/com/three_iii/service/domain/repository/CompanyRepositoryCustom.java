package com.three_iii.service.domain.repository;

import com.three_iii.service.application.dto.CompanyFindResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    Page<CompanyFindResponse> searchCompany(String keyword, Pageable pageable);

}

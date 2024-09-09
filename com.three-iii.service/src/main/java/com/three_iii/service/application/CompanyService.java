package com.three_iii.service.application;

import com.three_iii.service.application.dto.CompanyCreateRequest;
import com.three_iii.service.application.dto.CompanyCreateResponse;
import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyCreateResponse createCompany(CompanyCreateRequest requestDto) {
        Company company = CompanyCreateRequest.toEntity(requestDto);
        return CompanyCreateResponse.fromEntity(companyRepository.save(company));
    }
}

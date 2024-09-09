package com.three_iii.service.application;

import static com.three_iii.service.exception.ErrorCode.DUPLICATED_NAME;
import static com.three_iii.service.exception.ErrorCode.NOT_FOUND_COMPANY;

import com.three_iii.service.application.dto.CompanyCreateRequest;
import com.three_iii.service.application.dto.CompanyCreateResponse;
import com.three_iii.service.application.dto.CompanyFindResponse;
import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.repository.CompanyRepository;
import com.three_iii.service.exception.ApplicationException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyCreateResponse createCompany(CompanyCreateRequest requestDto) {
        // 업체명 중복 검사
        if (companyRepository.existsByName(requestDto.getName())) {
            throw new ApplicationException(DUPLICATED_NAME);
        }
        Company company = CompanyCreateRequest.toEntity(requestDto);
        return CompanyCreateResponse.fromEntity(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public CompanyFindResponse findCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));

        return CompanyFindResponse.fromEntity(company);
    }

    @Transactional(readOnly = true)
    public Page<CompanyFindResponse> findAllCompany(String keyword, Pageable pageable) {
        return companyRepository.searchCompany(keyword, pageable);
    }
}

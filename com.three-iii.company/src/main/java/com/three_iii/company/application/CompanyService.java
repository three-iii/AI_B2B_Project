package com.three_iii.company.application;

import static com.three_iii.company.exception.ErrorCode.DUPLICATED_NAME;
import static com.three_iii.company.exception.ErrorCode.NOT_FOUND_COMPANY;

import com.three_iii.company.application.dto.CompanyCreateRequest;
import com.three_iii.company.application.dto.CompanyResponse;
import com.three_iii.company.application.dto.CompanyUpdateRequest;
import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.repository.CompanyRepository;
import com.three_iii.company.exception.ApplicationException;
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
    public CompanyResponse createCompany(CompanyCreateRequest requestDto) {
        // 업체명 중복 검사
        if (companyRepository.existsByName(requestDto.getName())) {
            throw new ApplicationException(DUPLICATED_NAME);
        }
        Company company = Company.create(requestDto);
        return CompanyResponse.fromEntity(companyRepository.save(company));
    }

    @Transactional(readOnly = true)
    public CompanyResponse findCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));

        return CompanyResponse.fromEntity(company);
    }

    @Transactional(readOnly = true)
    public Page<CompanyResponse> findAllCompany(String keyword, Pageable pageable) {
        return companyRepository.searchCompany(keyword, pageable);
    }

    @Transactional
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));
        company.delete();
    }

    @Transactional
    public CompanyResponse updateCompany(UUID companyId, CompanyUpdateRequest requestDto) {
        Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));
        company.update(requestDto);
        return CompanyResponse.fromEntity(company);
    }
}

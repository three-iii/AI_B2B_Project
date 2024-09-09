package com.three_iii.service.application;

import static com.three_iii.service.exception.ErrorCode.DUPLICATED_NAME;

import com.three_iii.service.application.dto.CompanyCreateRequest;
import com.three_iii.service.application.dto.CompanyCreateResponse;
import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.repository.CompanyRepository;
import com.three_iii.service.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyCreateResponse createCompany(CompanyCreateRequest requestDto) {
        // 업체명 중복 검사
        if (companyRepository.findByName(requestDto.getName()).isPresent()) {
            throw new ApplicationException(DUPLICATED_NAME);
        }
        Company company = CompanyCreateRequest.toEntity(requestDto);
        return CompanyCreateResponse.fromEntity(companyRepository.save(company));
    }
}

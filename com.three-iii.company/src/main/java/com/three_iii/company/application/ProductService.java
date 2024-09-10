package com.three_iii.company.application;

import static com.three_iii.company.exception.ErrorCode.DUPLICATED_NAME;
import static com.three_iii.company.exception.ErrorCode.NOT_FOUND_COMPANY;

import com.three_iii.company.application.dto.ProductCreateRequest;
import com.three_iii.company.application.dto.ProductResponse;
import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.Product;
import com.three_iii.company.domain.repository.CompanyRepository;
import com.three_iii.company.domain.repository.ProductRepository;
import com.three_iii.company.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest requestDto) {
        // TODO 허브 검사
        // 업체 검사
        Company company = companyRepository.findById(requestDto.getCompanyId())
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));

        // 상품명 중복 검사
        if (productRepository.existsByNameAndCompany(requestDto.getName(), company)) {
            throw new ApplicationException(DUPLICATED_NAME);
        }

        Product product = Product.create(requestDto, company);
        return ProductResponse.fromEntity(productRepository.save(product));
    }
}

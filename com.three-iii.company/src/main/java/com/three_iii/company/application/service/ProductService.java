package com.three_iii.company.application.service;

import static com.three_iii.company.exception.ErrorCode.DUPLICATED_NAME;
import static com.three_iii.company.exception.ErrorCode.NOT_FOUND_COMPANY;
import static com.three_iii.company.exception.ErrorCode.NOT_FOUND_PRODUCT;

import com.three_iii.company.application.dtos.product.ProductDto;
import com.three_iii.company.application.dtos.product.ProductResponse;
import com.three_iii.company.application.dtos.product.ProductUpdateDto;
import com.three_iii.company.domain.Company;
import com.three_iii.company.domain.Product;
import com.three_iii.company.domain.repository.CompanyRepository;
import com.three_iii.company.domain.repository.ProductRepository;
import com.three_iii.company.exception.ApplicationException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final AiService aiService;

    @Transactional
    public ProductResponse createProduct(ProductDto request) {
        // TODO 허브 검사
        // 업체 검사
        Company company = getCompany(request.getCompanyId());

        // 상품명 중복 검사
        if (productRepository.existsByNameAndCompany(request.getName(), company)) {
            throw new ApplicationException(DUPLICATED_NAME);
        }

        Product product = Product.create(
            company,
            request.getHubId(),
            request.getName(),
            request.getDescription(),
            request.getQuantity());
        return ProductResponse.fromEntity(productRepository.save(product));
    }

    @Transactional
    public ProductResponse createProductByAi(ProductDto request) {
        Company company = getCompany(request.getCompanyId());

        String descriptionByAi = aiService.getCompletion(request.getDescription());
        Product product = Product.create(
            company,
            request.getHubId(),
            request.getName(),
            descriptionByAi,
            request.getQuantity());

        return ProductResponse.fromEntity(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(UUID productId) {
        Product product = getProduct(productId);
        return ProductResponse.fromEntity(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllProduct(String keyword, UUID hubId, UUID companyId,
        Pageable pageable) {
//        // TODO 허브 검사
        // 업체 검사
        if (companyId != null) {
            getCompany(companyId);
        }
        return productRepository.searchProduct(keyword, hubId, companyId, pageable);
    }

    @Transactional
    public ProductResponse updateProduct(ProductUpdateDto request, UUID productId) {
        Product product = getProduct(productId);
        product.update(request);
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = getProduct(productId);
        product.delete();
    }

    private Company getCompany(UUID companyId) {
        return companyRepository.findById(companyId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_COMPANY));
    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_PRODUCT));
    }

}

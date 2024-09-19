package com.three_iii.company.application.service;

import static com.three_iii.company.exception.ErrorCode.ACCESS_DENIED;
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
import com.three_iii.company.infrastructure.HubService;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final AiService aiService;
    private final HubService hubService;

    @Transactional
    public ProductResponse createProduct(ProductDto request, Long id, String role) {
        // 허브 검사
        hubService.findHub(request.getHubId());

        // 업체 검사
        Company company = getCompany(request.getCompanyId());

        //TODO 허브 관리자: 자신의 허브에 소속된 상품만 관리 가능

        // 허브 업체: 자신의 업체의 상품만 생성 및 수정 가능
        if (role.equals("COMPANY_MANAGER") && !Objects.equals(company.getUserId(), id)) {
            throw new ApplicationException(ACCESS_DENIED);
        }

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

        String descriptionByAi = aiService.getContents(request.getDescription());
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
        // 허브 검사
        if (hubId != null) {
            hubService.findHub(hubId);
        }
        // 업체 검사
        if (companyId != null) {
            getCompany(companyId);
        }
        return productRepository.searchProduct(keyword, hubId, companyId, pageable);
    }

    @Transactional
    public void updateProductStock(UUID productId, int newStockQuantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."));
        product.setQuantity(newStockQuantity);
        productRepository.save(product);
    }

    @Transactional
    public void restoreStock(UUID productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "해당 주문 항목에 해당하는 상품을 찾을 수 없습니다."));

        product.setStock(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    @Transactional
    public ProductResponse updateProduct(ProductUpdateDto request, UUID productId, Long id,
        String role) {
        Product product = getProduct(productId);

        // 허브 업체: 자신의 업체의 상품만 생성 및 수정 가능
        if (role.equals("COMPANY_MANAGER") && !Objects.equals(product.getCompany().getUserId(),
            id)) {
            throw new ApplicationException(ACCESS_DENIED);
        }

        product.update(request);
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public void deleteProduct(UUID productId, Long id, String role, String username) {
        Product product = getProduct(productId);

        // 허브 업체: 자신의 업체의 상품만 생성 및 수정 가능
        if (role.equals("COMPANY_MANAGER") && !Objects.equals(product.getCompany().getUserId(),
            id)) {
            throw new ApplicationException(ACCESS_DENIED);
        }

        product.delete(username);
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

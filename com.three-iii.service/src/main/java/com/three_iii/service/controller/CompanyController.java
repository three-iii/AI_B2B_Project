package com.three_iii.service.controller;

import com.three_iii.service.application.CompanyService;
import com.three_iii.service.application.dto.CompanyCreateRequest;
import com.three_iii.service.application.dto.CompanyCreateResponse;
import com.three_iii.service.application.dto.CompanyFindResponse;
import com.three_iii.service.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
@Tag(name = "Company API", description = "Company CRUD")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @Operation(summary = "업체 생성", description = "업체를 생성한다.")
    public Response<CompanyCreateResponse> createCompany(
        @RequestBody @Valid CompanyCreateRequest requestDto) {
        return Response.success(companyService.createCompany(requestDto));
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "업체 단건 조회", description = "업체를 단건 조회한다.")
    public Response<CompanyFindResponse> findCompany(@PathVariable UUID companyId) {
        return Response.success(companyService.findCompany(companyId));
    }

    @GetMapping
    @Operation(summary = "업체 전체 조회", description = "업체 전체 조회를 한다.")
    public Response<Page<CompanyFindResponse>> findAllCompany(
        @RequestParam(required = false) String keyword, Pageable pageable) {

        return Response.success(companyService.findAllCompany(keyword, pageable));
    }

}

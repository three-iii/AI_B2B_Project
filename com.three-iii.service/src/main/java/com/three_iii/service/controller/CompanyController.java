package com.three_iii.service.controller;

import com.three_iii.service.application.CompanyService;
import com.three_iii.service.application.dto.CompanyCreateRequest;
import com.three_iii.service.application.dto.CompanyCreateResponse;
import com.three_iii.service.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


}

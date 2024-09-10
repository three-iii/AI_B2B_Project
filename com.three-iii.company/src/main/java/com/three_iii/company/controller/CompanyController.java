package com.three_iii.company.controller;


import com.three_iii.company.application.CompanyService;
import com.three_iii.company.application.dto.CompanyCreateRequest;
import com.three_iii.company.application.dto.CompanyCreateResponse;
import com.three_iii.company.application.dto.CompanyFindResponse;
import com.three_iii.company.application.dto.CompanyUpdateRequest;
import com.three_iii.company.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @PatchMapping("/{companyId}")
    @Operation(summary = "업체 수정", description = "업체를 수정한다.")
    public Response<?> updateCompany(@PathVariable UUID companyId,
        @RequestBody CompanyUpdateRequest requestDto) {
        return Response.success(companyService.updateCompany(companyId, requestDto));
    }

    @DeleteMapping("/{companyId}")
    @Operation(summary = "업체 삭제", description = "업체를 삭제한다.")
    public Response<?> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return Response.success("해당 업체가 삭제되었습니다.");
    }
}

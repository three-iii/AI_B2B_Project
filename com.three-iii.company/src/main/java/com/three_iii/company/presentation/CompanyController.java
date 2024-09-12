package com.three_iii.company.presentation;


import com.three_iii.company.application.dtos.company.CompanyResponse;
import com.three_iii.company.application.service.CompanyService;
import com.three_iii.company.domain.UserPrincipal;
import com.three_iii.company.exception.Response;
import com.three_iii.company.presentation.dtos.CompanyCreateRequest;
import com.three_iii.company.presentation.dtos.CompanyUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER')")
    public Response<CompanyResponse> createCompany(
        @RequestBody @Valid CompanyCreateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(
            companyService.createCompany(request.toDTO(), userPrincipal.getId()));
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "업체 단건 조회", description = "업체를 단건 조회한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<CompanyResponse> findCompany(@PathVariable UUID companyId) {
        return Response.success(companyService.findCompany(companyId));
    }

    @GetMapping
    @Operation(summary = "업체 전체 조회", description = "업체 전체 조회를 한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<Page<CompanyResponse>> findAllCompany(
        @RequestParam(required = false) String keyword, Pageable pageable) {

        return Response.success(companyService.findAllCompany(keyword, pageable));
    }

    //TODO 허브 관리자: 자신의 허브에 소속된 업체만 관리 가능
    @PatchMapping("/{companyId}")
    @Operation(summary = "업체 수정", description = "업체를 수정한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER') or hasAuthority('COMPANY_MANAGER')")
    public Response<CompanyResponse> updateCompany(
        @PathVariable UUID companyId,
        @RequestBody CompanyUpdateRequest request,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(
            companyService.updateCompany(companyId, request.toDTO(), userPrincipal.getId(),
                userPrincipal.getRole()));
    }

    @DeleteMapping("/{companyId}")
    @Operation(summary = "업체 삭제", description = "업체를 삭제한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER') or hasAuthority('HUB_MANAGER')")
    public Response<?> deleteCompany(@PathVariable UUID companyId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        companyService.deleteCompany(companyId, userPrincipal.getUsername());
        return Response.success("해당 업체가 삭제되었습니다.");
    }
}

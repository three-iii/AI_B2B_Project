package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubResponse;
import com.three_iii.hub.application.service.HubService;
import com.three_iii.hub.exception.Response;
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
@RequestMapping("/api/hubs")
@Tag(name = "Hub API", description = "Hub CRUD")
public class HubController {

    private final HubService hubService;

    @PostMapping
    @Operation(summary = "허브 생성", description = "허브를 생성한다.")
    public Response<HubResponse> createHub(@RequestBody @Valid HubCreateRequest request) {
        return Response.success(hubService.createHub(request.toDTO()));
    }

    @GetMapping
    @Operation(summary = "허브 전체 조회", description = "허브를 전체 조회한다.")
    public Response<Page<HubResponse>> findAllHub(@RequestParam(required = false) String keyword,
        Pageable pageable) {
        return Response.success(hubService.findAllHub(keyword, pageable));
    }

    @GetMapping("/{hubId}")
    @Operation(summary = "허브 단건 조회", description = "허브를 단건 조회한다.")
    public Response<HubResponse> findHub(@PathVariable UUID hubId) {
        return Response.success(hubService.findHub(hubId));
    }

    @PatchMapping("/{companyId}")
    @Operation(summary = "허브 수정", description = "허브를 수정한다.")
    public Response<HubResponse> updateCompany(@PathVariable UUID companyId,
        @RequestBody HubUpdateRequest request) {
        return Response.success(hubService.updateHub(companyId, request.toDTO()));
    }

    @DeleteMapping("/{hubId}")
    @Operation(summary = "허브 삭제", description = "허브를 삭제한다.")
    public Response<?> deleteHub(@PathVariable UUID hubId) {
        hubService.deleteHub(hubId);
        return Response.success("해당 허브가 삭제되었습니다");
    }
}

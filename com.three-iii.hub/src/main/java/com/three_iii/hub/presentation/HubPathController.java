package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.application.service.HubPathService;
import com.three_iii.hub.exception.Response;
import com.three_iii.hub.presentation.dtos.HubPathCreateRequest;
import com.three_iii.hub.presentation.dtos.HubPathUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hub-paths")
@Tag(name = "Hub Path API", description = "Hub Path CRUD")
public class HubPathController {

    private final HubPathService hubPathService;

    @PostMapping
    @Operation(summary = "허브 간 이동정보 생성", description = "허브 간 이동정보를 생성한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<HubPathResponse> createHubPath(
        @RequestBody @Valid HubPathCreateRequest request) {
        return Response.success(hubPathService.createHubPath(request.toDTO()));
    }

    @GetMapping
    @Operation(summary = "허브 간 이동정보 전체 조회", description = "허브 간 이동정보를 전체 조회한다.")
    public Response<Page<HubPathResponse>> findAllHubPath(
        @RequestParam(required = false) String keyword,
        Pageable pageable) {
        return Response.success(hubPathService.findAllHubPath(keyword, pageable));
    }

    @GetMapping("/{hubPathId}")
    @Operation(summary = "허브 간 이동정보 단건 조회", description = "허브 간 이동정보를 단건 조회한다.")
    public Response<HubPathResponse> findHubPath(@PathVariable UUID hubPathId) {
        return Response.success(hubPathService.findHubPath(hubPathId));
    }

    @PatchMapping("/{hubPathId}")
    @Operation(summary = "허브 간 이동정보 수정", description = "허브 간 이동정보를 수정한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<HubPathResponse> updateHubPath(@PathVariable UUID hubPathId,
        @RequestBody HubPathUpdateRequest request) {
        return Response.success(hubPathService.updateHubPath(hubPathId, request.toDTO()));
    }

    @DeleteMapping("/{hubPathId}")
    @Operation(summary = "허브 간 이동정보 삭제", description = "허브 간 이동정보를 삭제한다.")
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<?> deleteHubPath(@PathVariable UUID hubPathId) {
        hubPathService.deleteHubPath(hubPathId);
        return Response.success("해당 허브 간 이동경로가 삭제되었습니다");
    }

}

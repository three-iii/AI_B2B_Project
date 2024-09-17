package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.application.service.HubPathService;
import com.three_iii.hub.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Response<HubPathResponse> createHub(@RequestBody @Valid HubPathCreateRequest request) {
        return Response.success(hubPathService.createHubPath(request.toDTO()));
    }

}

package com.three_iii.hub.presentation;

import com.three_iii.hub.application.dtos.HubResponse;
import com.three_iii.hub.application.service.HubService;
import com.three_iii.hub.exception.Response;
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
@RequestMapping("/api/hubs")
@Tag(name = "Hub API", description = "Hub CRUD")
public class HubController {

    private final HubService hubService;

    @PostMapping
    @Operation(summary = "허브 생성", description = "허브를 생성한다.")
    public Response<HubResponse> createHub(@RequestBody @Valid HubCreateRequest request) {
        return Response.success(hubService.createHub(request.toDTO()));
    }
}

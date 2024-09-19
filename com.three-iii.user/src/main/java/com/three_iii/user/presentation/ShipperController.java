package com.three_iii.user.presentation;

import com.three_iii.user.application.ShipperService;
import com.three_iii.user.domain.UserPrincipal;
import com.three_iii.user.exception.Response;
import com.three_iii.user.presentation.dtos.ShipperCreateRequest;
import com.three_iii.user.presentation.dtos.ShipperReadResponse;
import com.three_iii.user.presentation.dtos.ShipperUpdateRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<String> create(@RequestBody ShipperCreateRequest request) {
        return Response.success((shipperService.create(request).toString()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<List<ShipperReadResponse>> findAll() {
        return Response.success((shipperService.findAll()));
    }

    @GetMapping("/{shipperId}")
    public Response<ShipperReadResponse> find(@PathVariable(name = "shipperId") String shipperId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success((shipperService.find(UUID.fromString(shipperId), userPrincipal)));
    }

    @GetMapping("/type")
    public Response<List<ShipperReadResponse>> findByType(
        @RequestParam(name = "type") String type,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success((shipperService.findByType(type, userPrincipal)));
    }

    @PatchMapping("/{shipperId}")
    @PreAuthorize("hasAnyAuthority({'MASTER_MANAGER', 'HUB_MANAGER'})")
    public Response<String> update(
        @PathVariable(name = "shipperId") String shipperId,
        @RequestBody ShipperUpdateRequest request
    ) {
        return Response.success((shipperService.update(UUID.fromString(shipperId), request)));
    }

    @DeleteMapping("/{shipperId}")
    @PreAuthorize("hasAnyAuthority({'MASTER_MANAGER', 'HUB_MANAGER'})")
    public Response<String> delete(@PathVariable(name = "shipperId") String shipperId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        shipperService.delete(UUID.fromString(shipperId), userPrincipal);
        return Response.success("배송담당자 삭제를 완료했습니다.");
    }

}

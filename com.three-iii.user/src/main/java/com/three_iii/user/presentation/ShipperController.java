package com.three_iii.user.presentation;

import com.three_iii.user.application.ShipperService;
import com.three_iii.user.exception.Response;
import com.three_iii.user.presentation.dtos.ShipperCreateRequest;
import com.three_iii.user.presentation.dtos.ShipperReadResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<String> create(@RequestBody ShipperCreateRequest shipperCreateRequest) {
        return Response.success((shipperService.create(shipperCreateRequest.toDTO())).toString());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MASTER_MANAGER')")
    public Response<List<ShipperReadResponse>> findAll() {
        return Response.success((shipperService.findAll()));
    }

}

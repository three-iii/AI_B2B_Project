package com.three_iii.order.controller;

import com.three_iii.order.application.DeliveryPathService;
import com.three_iii.order.application.dto.DeliveryPathResponseDto;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.exception.Response;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping-paths")
public class DeliveryPathController {

    private final DeliveryPathService deliveryPathService;

    @GetMapping("/{shippingId}")
    public Response<DeliveryPathResponseDto> findDeliveryPath(@PathVariable UUID shippingId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(deliveryPathService.findDeliveryPath(shippingId, userPrincipal));
    }
}

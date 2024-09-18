package com.three_iii.order.controller;

import com.three_iii.order.domain.DeliveryPath;
import com.three_iii.order.domain.repository.DeliveryPathRepository;
import com.three_iii.order.exception.Response;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipping-paths")
public class DeliveryPathController {

    private final DeliveryPathRepository deliveryPathRepository;

    // 배송 경로 단건 조회
    @GetMapping("/{shippingId}")
    public Response<DeliveryPath> findDeliveryPath(@PathVariable UUID shippingId) {
        Optional<DeliveryPath> deliveryPath = deliveryPathRepository.findById(shippingId);
        if (deliveryPath.isPresent()) {
            return Response.success(deliveryPath.get());
        } else {
            return Response.error(deliveryPath.orElse(new DeliveryPath()));
        }
    }
}

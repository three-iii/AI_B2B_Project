package com.three_iii.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_delivery")
public class Delivery {

    @Id
    @GeneratedValue
    private UUID deliveryId;

    @JoinColumn
    private UUID orderId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatusEnum status;

    @Column(nullable = false)
    private UUID originHubId;

    @Column(nullable = false)
    private UUID destinationHubId;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false)
    private Boolean isDelete = false;
}

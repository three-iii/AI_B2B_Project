package com.three_iii.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "p_delivery_path")
public class DeliveryPath {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Column(name = "origin_hub_id", nullable = false)
    private UUID originHubId;

    @Column(name = "destination_hub_id", nullable = false)
    private UUID destinationHubId;

    @Column(name = "shipper_id", nullable = false)
    private UUID shipperId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "estimated_distance", nullable = false)
    private String estimatedDistance = "0.0";

    @Column(name = "estimated_duration", nullable = false)
    private String estimatedDuration = "0.0";

    @Column(name = "actual_distance", nullable = false)
    private String actualDistance = "0.0";

    @Column(name = "actual_duration", nullable = false)
    private String actualDuration = "0.0";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatusEnum status;
}

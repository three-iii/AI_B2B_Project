package com.three_iii.order.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
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
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID deliveryId;

    @OneToOne
    @JoinColumn
    private Order order;

    @Column
    private UUID productionCompany;

    @Column
    private UUID receiptCompany;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryPath> deliveryPaths = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatusEnum status;

    @Column(nullable = true)
    private UUID originHubId;

    @Column(nullable = true)
    private UUID destinationHubId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String slackId;

    //배송자 아이디
    private UUID shipperId;
}

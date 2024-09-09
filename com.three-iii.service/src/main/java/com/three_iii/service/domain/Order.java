package com.three_iii.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "p_order")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private UUID OrderItemId;

    @Column(name = "user_id")
    private BigInteger userId;

//    @ManyToOne
//    @JoinColumn(name = "request_company_id")
//    private RequestCompany requestCompanyId;
//
//    @ManyToOne
//    @JoinColumn(name = "receive_company_id")
//    private ReceiveCompany receiveCompanyId;

//    @ManyToOne
//    @JoinColumn(name = "delivery_id")
//    private Delivery deliveryId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status;

    @Column(nullable = false)
    private Boolean isDelete = false;



//    @OneToOne
//    @JoinColumn(name = "delivery_id")
//    private Delivety delivety;
}

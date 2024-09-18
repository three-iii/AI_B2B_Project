package com.three_iii.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity(name = "p_order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @JoinColumn(name = "product_id")
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;
}

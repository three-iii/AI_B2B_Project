package com.three_iii.order.domain;

import static com.three_iii.order.domain.OrderStatusEnum.COMPLETED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_delete = false")
@Entity(name = "p_order")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column
    private Long userId;

    private String userName;

    @Column(nullable = false)
    private UUID productionCompanyId;

    @Column(nullable = false)
    private UUID receiptCompanyId;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false)
    private String recipientName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToOne(optional = true)
    private Delivery delivery;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status = COMPLETED;


    // 주문 항목을 추가하는 메서드
    public void addOrderItem(OrderItem orderItem) {
        // OrderItem 객체에 현재 Order 객체를 설정
        orderItem.setOrder(this);
        // 주문 항목 리스트에 OrderItem을 추가
        orderItems.add(orderItem);
    }

    // OrderItem을 업데이트하는 메서드
    // 여러 개의 OrderItem을 업데이트하는 메서드
    public void updateOrderItem(List<OrderItem> updatedOrderItems) {
        for (OrderItem updatedOrderItem : updatedOrderItems) {
            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem currentItem = orderItems.get(i);
                if (currentItem.getOrderItemId().equals(updatedOrderItem.getOrderItemId())) {
                    orderItems.set(i, updatedOrderItem);  // 해당 아이템을 업데이트
                    break;
                }
            }
        }
    }
}

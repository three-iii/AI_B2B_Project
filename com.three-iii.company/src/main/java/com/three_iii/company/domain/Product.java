package com.three_iii.company.domain;

import com.three_iii.company.application.dtos.product.ProductUpdateDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_product")
@Where(clause = "is_delete = false")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Company company;

    private UUID hubId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int stock;

    public static Product create(Company company, UUID hubId, String name, String description,
        Integer quantity) {
        return Product.builder()
            .company(company)
            .hubId(hubId)
            .name(name)
            .description(description)
            .quantity(quantity)
            .build();
    }

    public void update(ProductUpdateDto requestDto) {
        this.name = requestDto.getName() == null ? this.name : requestDto.getName();
        this.quantity = requestDto.getQuantity() == null ? this.quantity : requestDto.getQuantity();
    }
}

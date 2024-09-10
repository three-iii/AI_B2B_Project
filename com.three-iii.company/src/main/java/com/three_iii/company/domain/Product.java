package com.three_iii.company.domain;

import com.three_iii.company.application.dto.ProductCreateRequest;
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

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_product")
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
    private int quantity;

    public static Product create(ProductCreateRequest requestDto, Company company) {
        return Product.builder()
            .company(company)
            .hubId(requestDto.getHubId())
            .name(requestDto.getName())
            .quantity(requestDto.getQuantity())
            .build();
    }
}

package com.three_iii.company.domain;

import com.three_iii.company.application.dtos.company.CompanyDto;
import com.three_iii.company.application.dtos.company.CompanyUpdateDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_company")
@Where(clause = "is_delete = false")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CompanyTypeEnum type;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();

    public static Company create(CompanyDto requestDto, Long userId) {
        return Company.builder()
            .hubId(requestDto.getHubId())
            .userId(userId)
            .name(requestDto.getName())
            .type(requestDto.getType())
            .address(requestDto.getAddress())
            .build();
    }

    public void update(CompanyUpdateDto requestDto) {
        this.name = requestDto.getName() == null ? this.name : requestDto.getName();
        this.type = requestDto.getType() == null ? this.type : requestDto.getType();
        this.address = requestDto.getAddress() == null ? this.address : requestDto.getAddress();
    }

    @Override
    public void delete(String username) {
        super.delete(username);
        for (Product product : productList) {
            product.delete(username);
        }
    }
}

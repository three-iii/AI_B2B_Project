package com.three_iii.service.domain;

import com.three_iii.service.application.dto.CompanyUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_company")
@Where(clause = "is_delete = false")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //TODO 안지연
    // hub 매핑
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn
//    private Hub hub;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CompanyTypeEnum type;

    @Column(nullable = false)
    private String address;

    public void update(CompanyUpdateRequest requestDto) {
        this.name = requestDto.getName() == null ? this.name : requestDto.getName();
        this.type = requestDto.getType() == null ? this.type : requestDto.getType();
        this.address = requestDto.getAddress() == null ? this.address : requestDto.getAddress();
    }
}

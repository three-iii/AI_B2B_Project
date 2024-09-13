package com.three_iii.hub.domain;

import com.three_iii.hub.application.dtos.HubDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "p_hub")
@Where(clause = "is_delete = false")
public class Hub extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String phone_number;

    public static Hub create(HubDto requestDto) {
        return Hub.builder()
            .name(requestDto.getName())
            .address(requestDto.getAddress())
            .latitude(requestDto.getLatitude())
            .longitude(requestDto.getLongitude())
            .phone_number(requestDto.getPhone_number())
            .build();
    }
}

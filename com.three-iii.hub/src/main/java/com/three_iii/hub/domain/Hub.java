package com.three_iii.hub.domain;

import com.three_iii.hub.application.dtos.HubDto;
import com.three_iii.hub.application.dtos.HubUpdateDto;
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
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

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

    public void update(HubUpdateDto requestDto) {
        this.name = requestDto.getName() == null ? this.name : requestDto.getName();
        this.address = requestDto.getAddress() == null ? this.address : requestDto.getAddress();
        this.latitude = requestDto.getLatitude() == null ? this.latitude : requestDto.getLatitude();
        this.longitude =
            requestDto.getLongitude() == null ? this.longitude : requestDto.getLongitude();
        this.phone_number =
            requestDto.getPhone_number() == null ? this.phone_number : requestDto.getPhone_number();
    }
}

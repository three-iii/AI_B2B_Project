package com.three_iii.hub.domain;

import com.three_iii.hub.application.dtos.HubPathUpdateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "p_hub_path")
@Where(clause = "is_delete = false")
public class HubPath extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "departure_id")
    private Hub departureId;

    @OneToOne
    @JoinColumn(name = "arrivals_id")
    private Hub arrivalsId;

    @Column(nullable = false)
    private String name; //이동 경로 전시명

    @Column(nullable = false)
    private String timeRequired;

    public static HubPath create(Hub departureId, Hub arrivalsId, String name,
        String timeRequired) {
        return HubPath.builder()
            .departureId(departureId)
            .arrivalsId(arrivalsId)
            .name(name)
            .timeRequired(timeRequired)
            .build();
    }

    public void update(Hub departureId, Hub arrivalsId, HubPathUpdateDto requestDto) {
        this.departureId = departureId == null ? this.departureId : departureId;
        this.arrivalsId = arrivalsId == null ? this.arrivalsId : arrivalsId;
        this.name = requestDto.getName() == null ? this.name : requestDto.getName();
        this.timeRequired =
            requestDto.getTimeRequired() == null ? this.timeRequired : requestDto.getTimeRequired();
    }
}

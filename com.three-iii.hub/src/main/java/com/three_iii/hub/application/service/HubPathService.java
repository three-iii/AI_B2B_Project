package com.three_iii.hub.application.service;

import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUB;

import com.three_iii.hub.application.dtos.HubPathDto;
import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.domain.Hub;
import com.three_iii.hub.domain.HubPath;
import com.three_iii.hub.domain.repository.HubPathRepository;
import com.three_iii.hub.domain.repository.HubRepository;
import com.three_iii.hub.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubPathService {

    private final HubRepository hubRepository;
    private final HubPathRepository hubPathRepository;

    @Transactional
    public HubPathResponse createHubPath(HubPathDto request) {
        Hub departurehub = hubRepository.findById(request.getDepartureId())
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));

        Hub arrivalsHub = hubRepository.findById(request.getArrivalsId())
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));

        HubPath hubPath = HubPath.create(departurehub, arrivalsHub, request.getName(),
            request.getTimeRequired());
        return HubPathResponse.fromEntity(hubPathRepository.save(hubPath));
    }
}

package com.three_iii.hub.application.service;

import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUB;
import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUBPATH;

import com.three_iii.hub.application.dtos.HubPathDto;
import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.domain.Hub;
import com.three_iii.hub.domain.HubPath;
import com.three_iii.hub.domain.repository.HubPathRepository;
import com.three_iii.hub.domain.repository.HubRepository;
import com.three_iii.hub.exception.ApplicationException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<HubPathResponse> findAllHubPath(String keyword, Pageable pageable) {
        return hubPathRepository.searchHubPath(keyword, pageable);
    }

    @Transactional(readOnly = true)
    public HubPathResponse findHubPath(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUBPATH));
        return HubPathResponse.fromEntity(hubPath);
    }
}

package com.three_iii.hub.application.service;

import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUB;
import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUBPATH;

import com.three_iii.hub.application.dtos.HubPathDto;
import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.application.dtos.HubPathUpdateDto;
import com.three_iii.hub.domain.Hub;
import com.three_iii.hub.domain.HubPath;
import com.three_iii.hub.domain.repository.HubPathRepository;
import com.three_iii.hub.domain.repository.HubRepository;
import com.three_iii.hub.exception.ApplicationException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @CachePut(cacheNames = "hubPathCache", key = "#result.id")
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
    @Cacheable(cacheNames = "hubPathAllCache", key = "methodName")
    public Page<HubPathResponse> findAllHubPath(String keyword, Pageable pageable) {
        return hubPathRepository.searchHubPath(keyword, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubPathCache", key = "args[0]")
    public HubPathResponse findHubPath(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUBPATH));
        return HubPathResponse.fromEntity(hubPath);
    }

    @Transactional
    @CachePut(cacheNames = "hubPathCache", key = "args[0]")
    @CacheEvict(cacheNames = "hubPathAllCache", allEntries = true)
    public HubPathResponse updateHubPath(UUID hubPathId, HubPathUpdateDto request) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUBPATH));

        // 출발 허브
        Hub departurehub = null;
        if (request.getDepartureId() != null) {
            departurehub = hubRepository.findById(request.getDepartureId())
                .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));

        }

        // 도착 허브
        Hub arrivalsHub = null;
        if (request.getArrivalsId() != null) {
            arrivalsHub = hubRepository.findById(request.getArrivalsId())
                .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        }

        hubPath.update(departurehub, arrivalsHub, request);
        return HubPathResponse.fromEntity(hubPath);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "hubPathCache", key = "args[0]"),
        @CacheEvict(cacheNames = "hubPathAllCache", allEntries = true)
    })
    public void deleteHubPath(UUID hubPathId) {
        HubPath hubPath = hubPathRepository.findById(hubPathId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUBPATH));

        hubPath.delete();
    }
}

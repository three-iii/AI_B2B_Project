package com.three_iii.hub.application.service;

import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUB;

import com.three_iii.hub.application.dtos.HubDto;
import com.three_iii.hub.application.dtos.HubResponse;
import com.three_iii.hub.application.dtos.HubUpdateDto;
import com.three_iii.hub.domain.Hub;
import com.three_iii.hub.domain.repository.HubRepository;
import com.three_iii.hub.exception.ApplicationException;
import com.three_iii.hub.presentation.dtos.HubNameFindRequest;
import com.three_iii.hub.presentation.dtos.HubNameFindResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
public class HubService {

    private final HubRepository hubRepository;

    @Transactional
    @CachePut(cacheNames = "hubCache", key = "#result.id")
    public HubResponse createHub(HubDto request) {
        Hub hub = Hub.create(request);
        return HubResponse.fromEntity(hubRepository.save(hub));
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubAllCache", key = "methodName")
    public Page<HubResponse> findAllHub(String keyword, Pageable pageable) {
        return hubRepository.searchHub(keyword, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "hubCache", key = "args[0]")
    public HubResponse findHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        return HubResponse.fromEntity(hub);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(cacheNames = "hubCache", key = "args[0]"),
        @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
    })
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        hub.delete();
    }

    @Transactional
    @CachePut(cacheNames = "hubCache", key = "args[0]")
    @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
    public HubResponse updateHub(UUID hubId, HubUpdateDto request) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        hub.update(request);
        return HubResponse.fromEntity(hub);
    }

    @Transactional(readOnly = true)
    public HubNameFindResponse findHubName(HubNameFindRequest request) {
        final List<Hub> hubs = hubRepository.findByIdIn(
            request.getHubIds().stream().map(hubId -> UUID.fromString(hubId)).collect(
                Collectors.toList()));
        return HubNameFindResponse.fromEntity(hubs);
    }
}

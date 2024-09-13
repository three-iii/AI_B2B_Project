package com.three_iii.hub.application.service;

import static com.three_iii.hub.exception.ErrorCode.NOT_FOUND_HUB;

import com.three_iii.hub.application.dtos.HubDto;
import com.three_iii.hub.application.dtos.HubResponse;
import com.three_iii.hub.application.dtos.HubUpdateDto;
import com.three_iii.hub.domain.Hub;
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
public class HubService {

    private final HubRepository hubRepository;

    @Transactional
    public HubResponse createHub(HubDto request) {
        Hub hub = Hub.create(request);
        return HubResponse.fromEntity(hubRepository.save(hub));
    }

    @Transactional(readOnly = true)
    public Page<HubResponse> findAllHub(String keyword, Pageable pageable) {
        return hubRepository.searchHub(keyword, pageable);
    }

    @Transactional(readOnly = true)
    public HubResponse findHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        return HubResponse.fromEntity(hub);
    }

    @Transactional
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        hub.delete();
    }

    @Transactional
    public HubResponse updateHub(UUID hubId, HubUpdateDto request) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(() -> new ApplicationException(NOT_FOUND_HUB));
        hub.update(request);
        return HubResponse.fromEntity(hub);
    }
}

package com.three_iii.hub.domain.repository;

import com.three_iii.hub.application.dtos.HubPathResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubPathRepositoryCustom {

    Page<HubPathResponse> searchHubPath(String keyword, Pageable pageable);
}

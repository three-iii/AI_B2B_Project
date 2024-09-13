package com.three_iii.hub.domain.repository;

import com.three_iii.hub.application.dtos.HubResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRepositoryCustom {

    Page<HubResponse> searchHub(String keyword, Pageable pageable);
}

package com.three_iii.company.infrastructure;

import com.three_iii.company.application.dtos.HubResponse;
import com.three_iii.company.exception.Response;
import java.util.UUID;

public interface HubService {

    Response<HubResponse> findHub(UUID hubId);
}

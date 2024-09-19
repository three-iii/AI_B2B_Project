package com.three_iii.slack.infrastructure;

import com.three_iii.slack.exception.Response;
import java.util.UUID;

public interface HubService {

    Response<HubResponse> findHub(UUID hubId);
}

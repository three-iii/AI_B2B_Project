package com.three_iii.user.application.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubNameFindRequest {

    List<String> hubIds = new ArrayList<>();

    public static HubNameFindRequest from(List<String> hubIds) {
        return new HubNameFindRequest(hubIds);
    }

}

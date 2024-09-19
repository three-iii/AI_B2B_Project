package com.three_iii.hub.presentation.dtos;

import com.three_iii.hub.domain.Hub;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HubNameFindResponse {

    HashMap<UUID, String> hubNames = new HashMap<>();

    public static HubNameFindResponse fromEntity(List<Hub> hubs) {
        HubNameFindResponse response = new HubNameFindResponse();
        hubs.forEach(hub -> response.hubNames.put(hub.getId(), hub.getName()));
        return response;
    }
}

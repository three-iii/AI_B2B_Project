package com.three_iii.hub.presentation.dtos;

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

}

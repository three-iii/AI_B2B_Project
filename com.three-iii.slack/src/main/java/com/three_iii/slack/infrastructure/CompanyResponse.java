package com.three_iii.slack.infrastructure;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private UUID hubId;
    private UUID id;
    private Long userId;
    private String name;
    private String type;
    private String address;
}

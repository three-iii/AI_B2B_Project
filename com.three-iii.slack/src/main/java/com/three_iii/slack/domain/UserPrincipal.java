package com.three_iii.slack.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    private final Long id;
    private final String username;
    private final String role;

    public static UserPrincipal of(Long id, String username, String role) {
        return new UserPrincipal(id, username, role);
    }
}

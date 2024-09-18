package com.three_iii.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    private final Long id;
    private final String username;
    private final String role;
    private String token;

    public static UserPrincipal of(Long id, String username, String role, String token) {
        return new UserPrincipal(id, username, role, token);
    }
}

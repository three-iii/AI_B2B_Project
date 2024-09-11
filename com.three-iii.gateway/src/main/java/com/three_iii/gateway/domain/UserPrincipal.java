package com.three_iii.gateway.domain;

import lombok.Getter;

@Getter
public class UserPrincipal {

    private final Long id;
    private final String username;
    private final String role;

    private UserPrincipal(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static UserPrincipal of(Long id, String username, String role) {
        return new UserPrincipal(id, username, role);
    }
}

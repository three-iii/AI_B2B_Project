package com.three_iii.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    MASTER_MANAGER("MASTER_MANAGER"),
    HUB_MANAGER("HUB_MANAGER"),
    COMPANY_MANAGER("COMPANY_MANAGER"),
    SHIPPER("SHIPPER"),
    CUSTOMER("CUSTOMER");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}

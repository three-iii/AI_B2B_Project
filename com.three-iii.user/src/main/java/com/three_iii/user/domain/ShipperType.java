package com.three_iii.user.domain;

import lombok.Getter;

@Getter
public enum ShipperType {
    HUB_SHIPPER("HUB_SHIPPER"),
    COMPANY_SHIPPER("COMPANY_SHIPPER");

    private String description;


    ShipperType(String description) {
        this.description = description;
    }
}

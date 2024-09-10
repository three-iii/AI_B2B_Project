package com.three_iii.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    MASTER_MANAGER("마스터 관리자"),
    HUB_MANAGER("허브 관리자"),
    COMPANY_MANAGER("업체 관리자"),
    SHIPPER("배송 담당자"),
    CUSTOMER("고객");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}

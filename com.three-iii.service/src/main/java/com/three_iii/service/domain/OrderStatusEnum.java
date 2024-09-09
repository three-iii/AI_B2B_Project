package com.three_iii.service.domain;

public enum OrderStatusEnum {
    COMPLETED(Status.COMPLETED),
    CANCELED(Status.CANCELED);

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static class Status {
        public static final String COMPLETED = "COMPLETED";
        public static final String CANCELED = "CANCELED";
    }
}

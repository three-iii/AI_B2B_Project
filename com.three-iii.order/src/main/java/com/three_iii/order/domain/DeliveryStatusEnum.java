package com.three_iii.order.domain;


public enum DeliveryStatusEnum {
    HUB_WAITING(Status.HUB_WAITING),
    HUB_MOVING(Status.HUB_MOVING),
    HUB_ARRIVED(Status.HUB_ARRIVED),
    SHIPPING(Status.SHIPPING);

    private final String status;

    DeliveryStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static class Status {

        public static final String HUB_WAITING = "HUB_WAITING";
        public static final String HUB_MOVING = "HUB_MOVING";
        public static final String HUB_ARRIVED = "HUB_ARRIVED";
        public static final String SHIPPING = "SHIPPING";
    }
}

package com.buy01.order.model;

import java.util.Set;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELED;

    private Set<OrderStatus> allowedNext;

    static {
        CREATED.allowedNext = Set.of(CONFIRMED, CANCELED);
        CONFIRMED.allowedNext = Set.of(SHIPPED, CANCELED);
        SHIPPED.allowedNext = Set.of(DELIVERED);
        DELIVERED.allowedNext = Set.of();
        CANCELED.allowedNext = Set.of();
    }

    public boolean canTransitionTo(OrderStatus next) {
        return allowedNext.contains(next);
    }
}


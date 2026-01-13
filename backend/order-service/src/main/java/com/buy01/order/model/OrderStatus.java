package com.buy01.order.model;

import java.util.Set;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    private Set<OrderStatus> allowedNext;

    static {
        CREATED.allowedNext = Set.of(CONFIRMED, CANCELLED);
        CONFIRMED.allowedNext = Set.of(SHIPPED, CANCELLED);
        SHIPPED.allowedNext = Set.of(DELIVERED);
        DELIVERED.allowedNext = Set.of();
        CANCELLED.allowedNext = Set.of();
    }

    public boolean canTransitionTo(OrderStatus next) {
        return allowedNext.contains(next);
    }
}


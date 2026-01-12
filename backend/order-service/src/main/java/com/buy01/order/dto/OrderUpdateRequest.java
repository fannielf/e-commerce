package com.buy01.order.dto;

import com.buy01.order.model.OrderStatus;
import jakarta.validation.Valid;

public class OrderUpdateRequest {
    @Valid
    private OrderStatus status;

    public OrderUpdateRequest() {}
    public OrderUpdateRequest(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}

}
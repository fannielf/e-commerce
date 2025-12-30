package com.buy01.order.dto;

import com.buy01.order.model.ShippingAddress;

public class OrderCreateDTO {
    private ShippingAddress shippingAddress;

    public OrderCreateDTO() {}
    public OrderCreateDTO(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingAddress getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(ShippingAddress shippingAddress) {this.shippingAddress = shippingAddress;}
}

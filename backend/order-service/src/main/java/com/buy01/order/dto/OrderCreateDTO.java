package com.buy01.order.dto;

import com.buy01.order.model.ShippingAddress;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class OrderCreateDTO {
    @NotNull(message = "Shipping address cannot be null")
    @Valid
    private ShippingAddress shippingAddress;

    public OrderCreateDTO() {}
    public OrderCreateDTO(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingAddress getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(ShippingAddress shippingAddress) {this.shippingAddress = shippingAddress;}
}

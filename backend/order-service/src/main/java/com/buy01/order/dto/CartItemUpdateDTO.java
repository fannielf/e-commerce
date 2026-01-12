package com.buy01.order.dto;

import jakarta.validation.constraints.Min;

public class CartItemUpdateDTO {
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    public CartItemUpdateDTO() {}
    public CartItemUpdateDTO(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}

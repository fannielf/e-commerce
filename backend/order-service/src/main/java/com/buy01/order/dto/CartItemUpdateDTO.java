package com.buy01.order.dto;

public class CartItemUpdateDTO {
    private int quantity;

    public CartItemUpdateDTO() {}
    public CartItemUpdateDTO(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}

package com.buy01.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CartItemRequestDTO {
    @NotBlank(message = "Product ID cannot be blank")
    private String productId;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    public CartItemRequestDTO() {}
    public CartItemRequestDTO(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {return this.productId;}
    public void setProductId(String productId) {this.productId = productId;}

    public Integer getQuantity() {return this.quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
}

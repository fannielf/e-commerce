package com.buy01.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ItemDTO {
    @NotBlank
    private String productId;
    @NotBlank
    private String productName;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    @Positive(message = "Price must be over 0")
    private double price;
    @Positive(message = "Subtotal must be over 0")
    private double subtotal;
    @NotBlank
    private String sellerId;

    public ItemDTO() {}
    public ItemDTO(String productId, String productName, int quantity, double price, double subtotal, String sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.sellerId = sellerId;
    }

    public String getProductId() {return this.productId;}
    public void setProductId(String productId) {this.productId = productId;}

    public String getProductName() {return this.productName;}
    public void setProductName(String productName) {this.productName = productName;}

    public int getQuantity() {return this.quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public double getPrice() {return this.price;}
    public void setPrice(double price) {this.price = price;}

    public double getSubtotal() {return this.subtotal;}
    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}

    public String getSellerId() {return this.sellerId;}
    public void setSellerId(String sellerId) {this.sellerId = sellerId;}
}

package com.buy01.order.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OrderItem {
    @NotBlank
    private String productId;
    @NotBlank
    private String productName;
    @Min(1)
    private int  quantity;
    @Positive
    private double price;
    @NotBlank
    private String sellerId;

    public OrderItem() {}

    public OrderItem(String productId, String productName, int quantity, double price, String sellerId) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
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

    public String getSellerId() {return this.sellerId;}
    public void setSellerId(String sellerId) {this.sellerId = sellerId;}

}

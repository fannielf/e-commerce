package com.buy01.order.dto;

import jakarta.validation.constraints.*;

public class ProductUpdateDTO {
    @NotBlank
    private String productId;
    @NotBlank
    private String productName;
    @NotNull(message = "Price is required")
    private Double productPrice;
    @NotNull(message = "Quantity is required")
    private int quantity;

    public ProductUpdateDTO() {}
    public ProductUpdateDTO(String productId, String productName, Double productPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public String getProductId() {return productId;}
    public void setProductId(String productId) {this.productId = productId;}

    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}

    public Double getProductPrice() {return productPrice;}
    public void setProductPrice(Double productPrice) {this.productPrice = productPrice;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}

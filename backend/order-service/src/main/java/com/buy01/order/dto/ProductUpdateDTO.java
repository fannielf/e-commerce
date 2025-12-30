package com.buy01.order.dto;

public class ProductUpdateDTO {
    private String productId;
    private String productName;
    private Double productPrice;

    public ProductUpdateDTO() {}
    public ProductUpdateDTO(String productId, String productName, Double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductId() {return productId;}
    public void setProductId(String productId) {this.productId = productId;}

    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}

    public Double getProductPrice() {return productPrice;}
    public void setProductPrice(Double productPrice) {this.productPrice = productPrice;}
}

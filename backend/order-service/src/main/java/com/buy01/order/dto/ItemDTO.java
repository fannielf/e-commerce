package com.buy01.order.dto;

public class ItemDTO {
    private String productId;
    private String name;
    private int quantity;
    private double price;
    private double subtotal;
    private String sellerId;

    public ItemDTO() {}
    public ItemDTO(String productId, String name, int quantity, double price, double subtotal, String sellerId) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.sellerId = sellerId;
    }

    public String getProductId() {return this.productId;}
    public void setProductId(String productId) {this.productId = productId;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public int getQuantity() {return this.quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public double getPrice() {return this.price;}
    public void setPrice(double price) {this.price = price;}

    public double getSubtotal() {return this.subtotal;}
    public void setSubtotal(double subtotal) {this.subtotal = subtotal;}

    public String getSellerId() {return this.sellerId;}
    public void setSellerId(String sellerId) {this.sellerId = sellerId;}
}

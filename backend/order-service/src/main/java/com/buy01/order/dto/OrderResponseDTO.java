package com.buy01.order.dto;

import com.buy01.order.model.OrderStatus;
import com.buy01.order.model.ShippingAddress;

import java.util.Date;
import java.util.List;

public class OrderResponseDTO {
    private String orderId;
    private List<ItemDTO> items;
    private double totalPrice;
    private OrderStatus status;
    private ShippingAddress shippingAddress;
    private Date createdAt;

    public OrderResponseDTO() {}
    public OrderResponseDTO(String orderId, List<ItemDTO> items, double totalPrice, OrderStatus status, ShippingAddress shippingAddress, Date createdAt) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
    }

    public String getOrderId() {return orderId;}
    public void setOrderId(String orderId) {this.orderId = orderId;}

    public List<ItemDTO> getItems() {return items;}
    public void setItems(List<ItemDTO> items) {this.items = items;}

    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}

    public ShippingAddress getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(ShippingAddress shippingAddress) {this.shippingAddress = shippingAddress;}

    public Date getCreatedAt() {return createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
}

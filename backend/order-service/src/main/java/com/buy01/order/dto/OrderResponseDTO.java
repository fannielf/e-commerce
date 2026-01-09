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
    private ShippingAddressMaskedDTO shippingAddress;
    private boolean paid;
    private Date deliveryDate;
    private String trackingNumber;
    private Date createdAt;
    private Date updatedAt;

    public OrderResponseDTO() {}
    public OrderResponseDTO(String orderId, List<ItemDTO> items, double totalPrice, OrderStatus status, ShippingAddressMaskedDTO shippingAddress, boolean paid, Date deliveryDate, String trackingNumber, Date createdAt, Date updatedAt) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.paid = paid;
        this.deliveryDate = deliveryDate;
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getOrderId() {return orderId;}
    public void setOrderId(String orderId) {this.orderId = orderId;}

    public List<ItemDTO> getItems() {return items;}
    public void setItems(List<ItemDTO> items) {this.items = items;}

    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}

    public ShippingAddressMaskedDTO getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(ShippingAddressMaskedDTO shippingAddress) {this.shippingAddress = shippingAddress;}

    public boolean isPaid() {return paid;}
    public void setPaid(boolean paid) {this.paid = paid;}

    public Date getDeliveryDate() {return deliveryDate;}
    public void setDeliveryDate(Date deliveryDate) {this.deliveryDate = deliveryDate;}

    public String getTrackingNumber() {return trackingNumber;}
    public void setTrackingNumber(String trackingNumber) {this.trackingNumber = trackingNumber;}

    public Date getCreatedAt() {return createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}

    public Date getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(Date updatedAt) {this.updatedAt = updatedAt;}
}

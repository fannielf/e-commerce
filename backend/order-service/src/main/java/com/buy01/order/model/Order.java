package com.buy01.order.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

@CompoundIndex(def = "{ 'items.sellerId': 1 }")
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    @NotBlank
    private String userId;
    @NotEmpty
    @Valid
    private List<OrderItem> items;
    @Positive
    private double totalPrice;
    @NotNull
    @Valid
    private OrderStatus status;
    @NotNull
    @Valid
    private ShippingAddress shippingAddress;
    private boolean paid;
    @FutureOrPresent
    private Date deliveryDate;
    private String trackingNumber;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    // constructor - both constructors are needed, empty one for Spring Data and one with parameters for creating objects
    public Order() {}
    public Order(String userId, List<OrderItem> items, ShippingAddress shippingAddress) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();        this.status = OrderStatus.CREATED;
        this.shippingAddress = shippingAddress;
        this.paid = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Testing purpose constructor
    protected Order(String id, String userId, List<OrderItem> items, double totalPrice, OrderStatus status, ShippingAddress shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.paid = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // getters and setters
    public String getId() {return id;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public List<OrderItem> getItems() {return items;}
    public void setItems(List<OrderItem> items) {this.items = items;}

    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}

    public ShippingAddress getShippingAddress() {return shippingAddress;}
    public void setShippingAddress(ShippingAddress shippingAddress) {this.shippingAddress = shippingAddress;}

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



package com.buy01.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> items;
    private double totalPrice;
    private Date createTime;
    private Date updateTime;

    public Cart(){}

    public Cart(String userId, List<OrderItem> items, double totalPrice) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    private Cart(String id, String userId, List<OrderItem> items, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public String getId() {return id;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public List<OrderItem> getItems() {return items;}
    public void setItems(List<OrderItem> items) {this.items = items;}

    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

    public Date getCreateTime() {return createTime;}
    public void setCreateTime(Date createTime) {this.createTime = createTime;}

    public Date getUpdateTime() {return updateTime;}
    public void setUpdateTime(Date updateTime) {this.updateTime = updateTime;}

}

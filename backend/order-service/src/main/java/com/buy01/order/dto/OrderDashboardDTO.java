package com.buy01.order.dto;

import java.util.List;

public class OrderDashboardDTO {
    private List<OrderResponseDTO> orders;
    private List<ItemDTO> topItems;
    private double total;

    public OrderDashboardDTO() {}
    public OrderDashboardDTO(List<OrderResponseDTO> orders, List<ItemDTO> topItems, double total) {
        this.orders = orders;
        this.topItems = topItems;
        this.total = total;
    }

    public List<OrderResponseDTO> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderResponseDTO> orders) {
        this.orders = orders;
    }
    public List<ItemDTO> getTopItems() {
        return topItems;
    }
    public void setTopItems(List<ItemDTO> topItems) {
        this.topItems = topItems;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}

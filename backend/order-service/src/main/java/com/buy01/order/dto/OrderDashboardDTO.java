package com.buy01.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDashboardDTO {
    @NotNull
    @Valid
    private List<OrderResponseDTO> orders;
    @NotNull
    @Valid
    private List<ItemDTO> topItems;
    @Min(value = 0, message = "Total cannot be negative")
    private double total;

    public OrderDashboardDTO() {}
    public OrderDashboardDTO(List<OrderResponseDTO> orders, List<ItemDTO> topItems, double total) {
        this.orders = orders != null ? orders : List.of();
        this.topItems = topItems != null ? topItems : List.of();
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

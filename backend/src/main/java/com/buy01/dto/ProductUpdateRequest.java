package com.buy01.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// DTO for updating an existing product - what the client sends in the request body
public class ProductUpdateRequest {
    private String name;
    private String description;

    @Min(value = 1, message = "Price must be at least 1")
    @Max(value = 100000, message = "Price cannot exceed 100000")
    private Double price;

    @Min(value = 1, message = "Price must be at least 1")
    private int quantity;

    public ProductUpdateRequest() {}
    public ProductUpdateRequest(String name, String description, Double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}


package com.buy01.order.dto;

import com.buy01.order.model.ShippingAddress;
import jakarta.validation.constraints.NotNull;

public class ShippingAddressMaskedDTO {
    @NotNull
    private String fullName;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String postalCode;
    @NotNull
    private String country;

    public ShippingAddressMaskedDTO() {}
    public ShippingAddressMaskedDTO(ShippingAddress shippingAddress) {
        this.fullName = maskName(shippingAddress.getFullName());
        this.street = maskStreet(shippingAddress.getStreet());
        this.city = maskCity(shippingAddress.getCity());
        this.postalCode = maskPostalCode(shippingAddress.getPostalCode());
        this.country = shippingAddress.getCountry();
    }

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getStreet() {return street;}
    public void setStreet(String street) {this.street = street;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getPostalCode() {return postalCode;}
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    private String maskName(String fullName) {
        if (fullName == null || fullName.isEmpty()) return "";
        String[] parts = fullName.split(" ");
        StringBuilder masked = new StringBuilder();
        for (String part : parts) {
            masked.append(part.charAt(0)) // first letter
                    .append("*".repeat(Math.max(0, part.length() - 1)))
                    .append(" ");
        }
        return masked.toString().trim();
    }

    private String maskStreet(String street) {
        if (street == null || street.length() < 3) return "**";
        return street.substring(0, 3) + "*".repeat(street.length() - 3);    }

    private String maskPostalCode(String postalCode) {
        if (postalCode == null || postalCode.length() < 2) return "**";
        return postalCode.substring(0, 2) + "*".repeat(postalCode.length() - 2);
    }

    private String maskCity(String city) {
        return city != null ?"*".repeat(city.length()) : "***";
    }
}
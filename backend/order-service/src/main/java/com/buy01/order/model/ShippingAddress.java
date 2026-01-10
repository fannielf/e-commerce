package com.buy01.order.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShippingAddress {
    @NotBlank
    @Size(min = 3, max = 25, message = "Full name must be at most 25 characters")
    private String fullName;
    @NotBlank
    @Size(min = 5, max = 100, message = "Street must be between 5 and 100 characters")
    private String street;
    @NotBlank
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;
    @NotBlank
    @Size(min = 4, max = 10, message = "Postal code must be between 4 and 10 characters")
    private String postalCode;
    @NotBlank
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    private String country;

    public ShippingAddress() {}
    public ShippingAddress(String fullName, String street, String city, String postalCode, String country) {
        this.fullName = fullName;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
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
}


package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Addresses {
    @Id
    @GeneratedValue
    private Long ID;
    private String street;
    private String streetNo;
    @Column(length = 4)
    private String postalCode;
    private String city;
    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonBackReference
    private Customers customers;

    public Addresses(String street, String streetNo, String postalCode, String city) {
        this.street = street;
        this.streetNo = streetNo;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Addresses() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}
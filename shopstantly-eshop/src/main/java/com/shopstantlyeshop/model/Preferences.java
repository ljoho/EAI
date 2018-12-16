package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Preferences {
    @Id
    @GeneratedValue
    private Long ID;
    private String name;
    @OneToOne(mappedBy = "preferences", fetch = FetchType.LAZY)
    @JsonBackReference
    private Products products;
    @ManyToOne
    @JsonBackReference
    private Customers customers;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}

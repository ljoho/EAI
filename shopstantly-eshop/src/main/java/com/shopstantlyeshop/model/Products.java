package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Products {
    @Id
    @GeneratedValue
    private Long ID;
    private String name;
    private Double price;
    @OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<OrderItems> orderItems = new HashSet<OrderItems>();
    @OneToOne
    @JsonManagedReference
    private Preferences preferences;

    public Products(String name, Double price, Set<OrderItems> orderItems, Preferences preferences) {
        this.name = name;
        this.price = price;
        this.orderItems = orderItems;
        this.preferences = preferences;
    }

    public Products() {
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}

package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderItems {
    @Id
    @GeneratedValue
    private Long ID;
    private int amount;
    private Double price;
    @ManyToOne
    @JsonBackReference
    private Orders order;
    @ManyToOne
    @JsonBackReference
    private Products products;

    public OrderItems(int amount, Double price, Orders orders, Products products) {
        this.amount = amount;
        this.price = price;
        this.order = orders;
        this.products = products;
    }

    public OrderItems() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

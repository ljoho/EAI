package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lib.Enums.PaymentType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long ID;
    private Date date;
    private Double price;
    private PaymentType paymentType;
    @ManyToOne
    @JsonBackReference
    private Customers customers;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderItems> orderItemsList = new HashSet<OrderItems>();

    public Orders(Date date, Double price, PaymentType paymentType, Customers customers, Set<OrderItems> orderItemsList) {
        this.date = date;
        this.price = price;
        this.paymentType = paymentType;
        this.customers = customers;
        this.orderItemsList = orderItemsList;
    }

    public Orders() {
        this.date = new java.sql.Date(System.currentTimeMillis());
        this.price = 0.00;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Set<OrderItems> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(Set<OrderItems> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }
}

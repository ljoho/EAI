package com.shopstantlypayment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lib.Network.Buffers.PaymentBuffer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transactions {
    @Id
    @GeneratedValue
    private Long ID;
    private Double price;
    private String transactionId;
    private Long orderId;
    @ManyToOne
    @JsonBackReference
    private Customers customers;

    public Transactions(PaymentBuffer paymentBuffer, Customers customer) {
        this.price = paymentBuffer.price;
        this.orderId = paymentBuffer.orderID;
        this.customers = customer;
    }

    public Transactions() {
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}

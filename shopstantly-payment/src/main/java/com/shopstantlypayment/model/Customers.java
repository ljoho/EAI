package com.shopstantlypayment.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customers {
    @Id
    @GeneratedValue
    private Long ID;
    private Long customersID;
    private int loyaltyPoints;
    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private Set<Transactions> transactions = new HashSet<Transactions>();

    public Customers(Long customersID) {
        this.customersID = customersID;
    }

    public Customers() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getCustomersID() {
        return customersID;
    }

    public void setCustomersID(Long customersID) {
        this.customersID = customersID;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Set<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transactions> transactions) {
        this.transactions = transactions;
    }
}

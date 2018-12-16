package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lib.Enums.CreditCardsType;

import javax.persistence.*;

@Entity
public class CreditCards {
    @Id
    @GeneratedValue
    private Long ID;
    @Enumerated
    private CreditCardsType creditCardsType;
    private String number;
    private int expMonth;
    private int expYear;
    private String name;
    private Boolean isPrimary;
    @ManyToOne
    @JsonBackReference
    private Customers customers;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public CreditCardsType getCreditCardsType() {
        return creditCardsType;
    }

    public void setCreditCardsType(CreditCardsType creditCardsType) {
        this.creditCardsType = creditCardsType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }
}

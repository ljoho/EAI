package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Carts {
    @Id
    @GeneratedValue
    private Long ID;
    @OneToOne(mappedBy = "carts", fetch = FetchType.LAZY)
    @JsonBackReference
    private Customers customers;
    @ElementCollection
    private List<Products> productsList = new ArrayList<Products>();

    public Carts(Customers customers) {
        this.customers = customers;
    }

    public Carts() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}

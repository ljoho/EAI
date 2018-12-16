package com.shopstantlyinventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Products {
    @Id
    @GeneratedValue
    private Long ID;
    private Long productsID;
    private String place;
    private int stock;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getProductsID() {
        return productsID;
    }

    public void setProductsID(Long productsID) {
        this.productsID = productsID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

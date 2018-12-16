package com.shopstantlyeshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lib.Enums.Gender;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customers {
    @Id
    @GeneratedValue
    private Long ID;
    private String lastname;
    private String firstname;
    @Enumerated
    private Gender gender;
    @Email
    private String email;
    @Transient
    private String password;
    @OneToOne
    @JsonManagedReference
    private Addresses address;
    @OneToOne
    @JsonManagedReference
    private Carts carts;
    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private Set<Orders> orders = new HashSet<Orders>();
    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private Set<Preferences> preferences = new HashSet<Preferences>();
    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private Set<CreditCards> creditCards = new HashSet<CreditCards>();

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<Preferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<Preferences> preferences) {
        this.preferences = preferences;
    }

    public Set<CreditCards> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCards> creditCards) {
        this.creditCards = creditCards;
    }
}

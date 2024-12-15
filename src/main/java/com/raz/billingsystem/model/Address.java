package com.raz.billingsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int adr_id;
    private String city;
    private String state;
    private String country;

    public int getAdr_id() {
        return adr_id;
    }

    public void setAdr_id(int adr_id) {
        this.adr_id = adr_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}

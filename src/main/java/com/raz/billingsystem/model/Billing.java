package com.raz.billingsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int b_id;

    private String fname;
    private String lname;
    private String phone;
    private String email;
    private String city;
    private String country;
    private String pack_name;
    private long pack_price;
    private String status;

    private long total;

    @Temporal(TemporalType.DATE)
    private Date date;
}

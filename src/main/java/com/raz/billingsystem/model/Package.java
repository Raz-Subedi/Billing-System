package com.raz.billingsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pname;
    private long pprice;
}

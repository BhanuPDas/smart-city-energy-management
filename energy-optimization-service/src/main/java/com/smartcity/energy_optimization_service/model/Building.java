package com.smartcity.energy_optimization_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String address;

    private String zipCode;

    private String ownerEmail;

    private double floorArea;
}

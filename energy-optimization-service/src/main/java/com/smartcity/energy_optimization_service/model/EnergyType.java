package com.smartcity.energy_optimization_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EnergyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String energyType;

    private String unit;

    private String provider;

    private double pricePerUnit;
}

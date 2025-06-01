package com.smartcity.energy_optimization_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "energy_type", schema = "rbh_mgmt")
public class EnergyTypeEntity {

    @Id
    private Long id;
    @Column(name = "name")
    private String energyType;
    private String unit;
    private String provider;
    @Column(name = "price_per_unit")
    private double pricePerUnit;
}

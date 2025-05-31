package com.fhdo.energy_analysis_service.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "energy_type", schema = "rbh_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyType {

    @Id
    private Long id;

    private String name;
    private String provider;
    private String unit;

    @Column(name = "price_per_unit")
    private Integer pricePerUnit;
}

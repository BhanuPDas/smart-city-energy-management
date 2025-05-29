package com.smartcity.energy_optimization_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class EnergySource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "energy_type_id", nullable = false)
    private EnergyType energyType;

    private LocalDate startDate;

    private LocalDate endDate;

    private double consumption;
}

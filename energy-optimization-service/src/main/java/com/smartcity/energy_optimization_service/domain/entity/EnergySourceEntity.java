package com.smartcity.energy_optimization_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "energy_source", schema = "rbh_mgmt")
public class EnergySourceEntity {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingEntity building;
    @ManyToOne
    @JoinColumn(name = "energy_type_id", referencedColumnName = "id")
    private EnergyTypeEntity energyType;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    private Integer consumption;
}

package com.fhdo.energy_analysis_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy_source", schema = "rbh_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySource {

    @Id
    private Long id;

    private Integer consumption;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "energy_type_id", referencedColumnName = "id")
    private EnergyType energyType;
}

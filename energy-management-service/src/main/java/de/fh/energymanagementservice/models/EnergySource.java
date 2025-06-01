package de.fh.energymanagementservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
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

    @Column
    private  Date startDate;

    @Column
    private  Date endDate;

    @Column
    private  int consumption;


}

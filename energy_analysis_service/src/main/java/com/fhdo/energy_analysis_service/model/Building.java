package com.fhdo.energy_analysis_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "building", schema = "rbh_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    @Id
    private Long id;

    @Column(name = "owner_email")
    private String ownerEmail;

    private String address;
    private String city;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "floor_area")
    private Integer floorArea;
}



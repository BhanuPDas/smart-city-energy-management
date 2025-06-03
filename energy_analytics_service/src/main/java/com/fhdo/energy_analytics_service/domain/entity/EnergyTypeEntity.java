package com.fhdo.energy_analytics_service.domain.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "energy_type", schema = "rbh_mgmt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyTypeEntity {

	@Id
	private Long id;
	private String name;
	private String provider;
	private String unit;
	@Column(name = "price_per_unit")
	private double pricePerUnit;
}

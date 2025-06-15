package com.smartcity.energy_optimization_service.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "building", schema = "rbh_mgmt")
public class BuildingEntity {

	@Id
	private Long id;
	private String city;
	private String address;
	@Column(name = "zip_code")
	private String zipCode;
	@Column(name = "owner_email")
	private String ownerEmail;
	@Column(name = "floor_area")
	private double floorArea;
}

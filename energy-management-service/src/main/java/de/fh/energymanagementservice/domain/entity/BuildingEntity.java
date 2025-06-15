package de.fh.energymanagementservice.domain.entity;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

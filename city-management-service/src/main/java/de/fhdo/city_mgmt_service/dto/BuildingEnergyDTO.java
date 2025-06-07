package de.fhdo.city_mgmt_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingEnergyDTO {

	private String city;
	private String address;
	private int zipCode;
	private String ownerEmail;
	private int floorArea;
	private String energyType;
	private LocalDate startDate;
	private LocalDate endDate;
	private int consumption;
}

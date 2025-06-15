package de.fhdo.city_mgmt_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySourceDTO {

	private String ownerEmail;
	private String energyType;
	private LocalDate startDate;
	private LocalDate endDate;
	private int consumption;
}

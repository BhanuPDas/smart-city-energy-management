package de.fhdo.city_mgmt_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyAnalyticsDTO {

	private String ownerEmail;
	private LocalDate startDate;
	private String city;
	private int zipCode;
}

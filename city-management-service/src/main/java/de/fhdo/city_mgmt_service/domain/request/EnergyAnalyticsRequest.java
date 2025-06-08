package de.fhdo.city_mgmt_service.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyAnalyticsRequest {

	private String ownerEmail;
	private String startDate;
	private String city;
	private int zipCode;
}

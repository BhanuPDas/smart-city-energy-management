package de.fhdo.city_mgmt_service.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

	private String address;
	private int consumption;
	private String energyType;
	private String unit;
	private String city;
	private int zipcode;
	private String provider;
}
